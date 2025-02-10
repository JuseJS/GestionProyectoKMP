package presentation.viewmodel

import data.network.rest.model.requests.AssignProgrammerRequest
import data.network.rest.model.requests.AssignTaskRequest
import data.network.rest.model.requests.CreateTaskRequest
import data.store.UserStore
import domain.common.Result
import domain.model.Programmer
import domain.model.Project
import domain.repository.ProjectRepository
import domain.repository.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import presentation.states.ProjectDetailState
import presentation.states.UiState

class ProjectDetailViewModel(
    private val taskRepository: TaskRepository,
    private val projectRepository: ProjectRepository,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)
) {
    private val _currentProject = MutableStateFlow<Project?>(null)
    private val _projectProgrammers = MutableStateFlow<List<Programmer>>(emptyList())
    private val _availableProgrammers = MutableStateFlow<List<Programmer>>(emptyList())

    val projectProgrammers: StateFlow<List<Programmer>> = _projectProgrammers.asStateFlow()
    val availableProgrammers: StateFlow<List<Programmer>> = _availableProgrammers.asStateFlow()

    // Estado de la UI
    val uiState: StateFlow<UiState<ProjectDetailState>> = taskRepository.projectDetailState

    fun initProject(project: Project) {
        _currentProject.value = project
        loadData()
        loadProgrammers(project.id)
        loadAvailableProgrammers()
    }

    fun refresh() {
        loadData()
        _currentProject.value?.let { project ->
            loadProgrammers(project.id)
            loadAvailableProgrammers()
        }
    }

    private fun loadData() {
        scope.launch {
            _currentProject.value?.let { project ->
                taskRepository.loadProjectTasks(project)
            }
        }
    }

    fun loadProgrammers(projectId: Int) {
        scope.launch {
            when (val result = projectRepository.getProjectProgrammers(projectId)) {
                is Result.Success -> {
                    _projectProgrammers.value = result.data
                }
                is Result.Error -> {
                    _projectProgrammers.value = emptyList()
                    println("[ERROR] Failed to load project programmers: ${result.exception.message}")
                }
            }
        }
    }

    private fun loadAvailableProgrammers() {
        scope.launch {
            when (val result = projectRepository.getAllAvailableProgrammers()) {
                is Result.Success -> {
                    val currentProgrammerIds = _projectProgrammers.value.map { it.id }.toSet()
                    _availableProgrammers.value = result.data.filterNot {
                        currentProgrammerIds.contains(it.id)
                    }
                }
                is Result.Error -> {
                    _availableProgrammers.value = emptyList()
                    println("[ERROR] Failed to load available programmers: ${result.exception.message}")
                }
            }
        }
    }

    fun assignProgrammerToProject(programmerId: Int) {
        scope.launch {
            _currentProject.value?.let { project ->
                val request = AssignProgrammerRequest(programmerId, project.id)
                when (val result = projectRepository.assignProgrammer(request)) {
                    is Result.Success -> {
                        loadProgrammers(project.id)
                        loadAvailableProgrammers()
                    }
                    is Result.Error -> {
                        println("[ERROR] Failed to assign programmer: ${result.exception.message}")
                    }
                }
            }
        }
    }

    fun assignTask(taskId: Int, programmerId: Int) {
        scope.launch {
            val request = AssignTaskRequest(programmerId, taskId)
            when (val result = taskRepository.assignTask(request)) {
                is Result.Success -> loadData()
                is Result.Error -> {
                    println("[ERROR] Failed to assign task: ${result.exception.message}")
                }
            }
        }
    }

    fun createTask(
        name: String,
        description: String,
        estimation: Int,
        programmerId: Int? = null
    ) {
        scope.launch {
            _currentProject.value?.let { project ->
                val currentUser = UserStore.currentUser.value
                if (currentUser == null) {
                    println("[ERROR] No user logged in")
                    return@launch
                }

                val request = CreateTaskRequest(
                    manager = currentUser.id,
                    name = name,
                    description = description,
                    estimation = estimation,
                    project = project.id,
                    programmer = programmerId
                )

                println("[DEBUG] Creating task with request: $request")

                when (val result = taskRepository.createTask(request)) {
                    is Result.Success -> {
                        println("[DEBUG] Task created successfully")
                        loadData()
                    }
                    is Result.Error -> {
                        println("[ERROR] Failed to create task: ${result.exception.message}")
                    }
                }
            } ?: run {
                println("[ERROR] Cannot create task: Current project is null")
            }
        }
    }
}