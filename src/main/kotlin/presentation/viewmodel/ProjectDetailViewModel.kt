package presentation.viewmodel

import data.network.rest.model.requests.AssignTaskRequest
import data.network.rest.model.requests.CreateTaskRequest
import data.network.rest.model.responses.ProjectProgrammerResponse
import data.store.UserStore
import domain.common.Result
import domain.model.Project
import domain.model.User
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
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)
) {
    private val _currentProject = MutableStateFlow<Project?>(null)
    private val _programmers = MutableStateFlow<List<ProjectProgrammerResponse>>(emptyList())
    val programmers: StateFlow<List<ProjectProgrammerResponse>> = _programmers.asStateFlow()

    // Estado de la UI
    val uiState: StateFlow<UiState<ProjectDetailState>> = taskRepository.projectDetailState

    fun initProject(project: Project) {
        _currentProject.value = project
        loadData()
    }

    fun loadProgrammers(projectId: Int) {
        scope.launch {
            when (val result = taskRepository.getProjectProgrammers(projectId)) {
                is Result.Success -> _programmers.value = result.data
                is Result.Error -> _programmers.value = emptyList()
            }
        }
    }

    fun refresh() {
        loadData()
        _currentProject.value?.let { project ->
            loadProgrammers(project.id)
        }
    }

    private fun loadData() {
        scope.launch {
            _currentProject.value?.let { project ->
                taskRepository.loadProjectTasks(project)
            }
        }
    }

    fun assignTask(taskId: Int, programmerId: Int) {
        scope.launch {
            val request = AssignTaskRequest(programmerId, taskId)
            when (taskRepository.assignTask(request)) {
                is Result.Success -> loadData()
                is Result.Error -> {
                    // TODO: Manejar error de asignación
                }
            }
        }
    }

    suspend fun getProjectProgrammers(projectId: Int): List<ProjectProgrammerResponse> =
        when (val result = taskRepository.getProjectProgrammers(projectId)) {
            is Result.Success -> result.data
            is Result.Error -> emptyList()
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