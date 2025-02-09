// data/repository/TaskRepositoryImpl.kt
package data.repository

import data.network.rest.ApiService
import data.network.rest.model.mappers.toDomain
import data.network.rest.model.requests.CreateTaskRequest
import data.network.rest.model.requests.AssignTaskRequest
import domain.common.Result
import domain.model.Task
import domain.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import presentation.states.ProjectDetailState
import presentation.states.TasksState
import presentation.states.UiState

class TaskRepositoryImpl(
    private val apiService: ApiService
) : TaskRepository {

    private val _projectDetailState = MutableStateFlow<UiState<ProjectDetailState>>(UiState.Loading)
    override val projectDetailState: StateFlow<UiState<ProjectDetailState>> = _projectDetailState.asStateFlow()

    private val _tasksState = MutableStateFlow<UiState<TasksState>>(UiState.Loading)
    override val tasksState: StateFlow<UiState<TasksState>> = _tasksState.asStateFlow()

    override suspend fun loadAllTasks() {
        _tasksState.value = UiState.Loading

        try {
            when (val unassignedResult = getUnassignedTasks()) {
                is Result.Success -> {
                    val unassignedTasks = unassignedResult.data

                    when (val assignedResult = getAssignedTasks()) {
                        is Result.Success -> {
                            when (val completedResult = getCompletedTasks()) {
                                is Result.Success -> {
                                    _tasksState.value = UiState.Success(
                                        TasksState(
                                            unassignedTasks = unassignedTasks,
                                            assignedTasks = assignedResult.data,
                                            completedTasks = completedResult.data
                                        )
                                    )
                                }
                                is Result.Error -> {
                                    _tasksState.value = UiState.Error(
                                        completedResult.exception.message ?:
                                        "Error al cargar tareas completadas"
                                    )
                                }
                            }
                        }
                        is Result.Error -> {
                            _tasksState.value = UiState.Error(
                                assignedResult.exception.message ?:
                                "Error al cargar tareas asignadas"
                            )
                        }
                    }
                }
                is Result.Error -> {
                    _tasksState.value = UiState.Error(
                        unassignedResult.exception.message ?: "Error al cargar tareas sin asignar"
                    )
                }
            }
        } catch (e: Exception) {
            _tasksState.value = UiState.Error(e.message ?: "Error desconocido")
        }
    }

    override suspend fun loadProjectTasks(projectId: Int) {
        _projectDetailState.value = UiState.Loading

        try {
            when (val projectResult = apiService.getProjectDetails(projectId.toString())) {
                is Result.Success -> {
                    val project = projectResult.data.toDomain()

                    when (val tasksResult = getProjectTasks(projectId)) {
                        is Result.Success -> {
                            _projectDetailState.value = UiState.Success(
                                ProjectDetailState(
                                    project = project,
                                    tasks = tasksResult.data
                                )
                            )
                        }
                        is Result.Error -> {
                            _projectDetailState.value = UiState.Error(
                                tasksResult.exception.message ?: "Error al cargar las tareas del proyecto"
                            )
                        }
                    }
                }
                is Result.Error -> {
                    _projectDetailState.value = UiState.Error(
                        projectResult.exception.message ?: "Error al cargar los detalles del proyecto"
                    )
                }
            }
        } catch (e: Exception) {
            _projectDetailState.value = UiState.Error(e.message ?: "Error desconocido")
        }
    }

    override suspend fun getUnassignedTasks(): Result<List<Task>> = try {
        val response = apiService.getUnassignedTasks()
        Result.Success(response.map { it.toDomain() })
    } catch (e: Exception) {
        Result.Error(e)
    }

    override suspend fun getAssignedTasks(): Result<List<Task>> = try {
        val response = apiService.getAssignedTasks()
        Result.Success(response.map { it.toDomain() })
    } catch (e: Exception) {
        Result.Error(e)
    }

    override suspend fun getCompletedTasks(): Result<List<Task>> = try {
        val response = apiService.getCompletedTasks()
        Result.Success(response.map { it.toDomain() })
    } catch (e: Exception) {
        Result.Error(e)
    }

    override suspend fun getProjectTasks(projectId: Int): Result<List<Task>> = try {
        val response = apiService.getProjectTasks(projectId)
        Result.Success(response.map { it.toDomain() })
    } catch (e: Exception) {
        Result.Error(e)
    }

    override suspend fun getTaskDetails(id: Int): Result<Task> = try {
        val response = apiService.getTaskDetails(id)
        Result.Success(response.toDomain())
    } catch (e: Exception) {
        Result.Error(e)
    }

    override suspend fun createTask(request: CreateTaskRequest): Result<Task> = try {
        val response = apiService.createTask(request)
        Result.Success(response.toDomain())
    } catch (e: Exception) {
        Result.Error(e)
    }

    override suspend fun assignTask(request: AssignTaskRequest): Result<Task> = try {
        val response = apiService.assignTask(request)
        Result.Success(response.toDomain())
    } catch (e: Exception) {
        Result.Error(e)
    }

    override suspend fun completeTask(taskId: Int): Result<Task> = try {
        val response = apiService.completeTask(taskId)
        Result.Success(response.toDomain())
    } catch (e: Exception) {
        Result.Error(e)
    }
}