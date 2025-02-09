package presentation.viewmodel

import data.network.rest.model.requests.AssignTaskRequest
import data.network.rest.model.requests.CreateTaskRequest
import domain.common.Result
import domain.model.Project
import domain.model.User
import domain.repository.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import presentation.states.ProjectDetailState
import presentation.states.UiState

class ProjectDetailViewModel(
    private val taskRepository: TaskRepository,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)
) {
    private val _currentProject = MutableStateFlow<Project?>(null)
    private val _currentUser = MutableStateFlow<User?>(null)

    // Estado de la UI
    val uiState: StateFlow<UiState<ProjectDetailState>> = taskRepository.projectDetailState

    fun initProject(project: Project) {
        _currentProject.value = project
        loadData()
    }

    private fun loadData() {
        scope.launch {
            _currentProject.value?.let { project ->
                taskRepository.loadProjectTasks(project)
            }
        }
    }

    fun refresh() {
        loadData()
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

    fun createTask(
        name: String,
        description: String,
        estimation: Int,
        programmerId: Int? = null
    ) {
        scope.launch {
            _currentProject.value?.let { project ->
                val request = CreateTaskRequest(
                    manager = _currentUser.value?.id ?: 1,
                    name = name,
                    description = description,
                    estimation = estimation,
                    project = project.id,
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