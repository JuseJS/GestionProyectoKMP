package presentation.viewmodel

import data.network.rest.model.requests.AssignProgrammerRequest
import data.network.rest.model.requests.AssignTaskRequest
import data.network.rest.model.requests.CreateTaskRequest
import domain.common.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import presentation.states.UiState
import domain.repository.ProjectRepository
import domain.repository.TaskRepository
import domain.model.Project
import presentation.states.ProjectDetailState

class ProjectDetailViewModel(
    private val projectRepository: ProjectRepository,
    private val taskRepository: TaskRepository,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)
) {
    private val _currentProject = MutableStateFlow<Project?>(null)

    // Estado de la UI
    val uiState: StateFlow<UiState<ProjectDetailState>> = taskRepository.projectDetailState

    fun initProject(project: Project) {
        _currentProject.value = project
        loadData()
    }

    private fun loadData() {
        scope.launch {
            _currentProject.value?.let { project ->
                taskRepository.loadProjectTasks(project.id)
            }
        }
    }

    fun refresh() {
        loadData()
    }

    fun assignProgrammer(project: Project, programmerId: Int) {
        scope.launch {
            val request = AssignProgrammerRequest(programmerId, project.id)
            when (taskRepository.assignTask(request)) {
                is Result.Success -> loadData()
                is Result.Error -> {
                    // TODO: Manejar error de asignación
                }
            }
        }
    }

    fun assignTask(taskId: Int, programmerId: Int) {
        scope.launch {
            val request = AssignTaskRequest(taskId, programmerId)
            when (taskRepository.assignTask(request)) {
                is Result.Success -> loadData()
                is Result.Error -> {
                    // TODO: Manejar error de asignación
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
                val request = CreateTaskRequest(
                    name = name,
                    description = description,
                    estimation = estimation,
                    projectId = project.id,
                    programmerId = programmerId
                )

                when (taskRepository.createTask(request)) {
                    is Result.Success -> loadData()
                    is Result.Error -> {
                        // TODO: Manejar error de creación
                    }
                }
            }
        }
    }
}