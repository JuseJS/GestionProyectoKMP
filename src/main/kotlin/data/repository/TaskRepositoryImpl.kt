// data/repository/TaskRepositoryImpl.kt
package data.repository

import data.network.rest.ApiService
import data.network.rest.model.mappers.toDomain
import data.network.rest.model.requests.CreateTaskRequest
import data.network.rest.model.requests.AssignTaskRequest
import data.network.rest.model.responses.ProjectProgrammerResponse
import domain.common.Result
import domain.model.Task
import domain.model.Project
import domain.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import presentation.states.ProjectDetailState
import presentation.states.UiState

class TaskRepositoryImpl(
    private val apiService: ApiService
) : TaskRepository {

    private val _projectDetailState = MutableStateFlow<UiState<ProjectDetailState>>(UiState.Loading)
    override val projectDetailState: StateFlow<UiState<ProjectDetailState>> = _projectDetailState.asStateFlow()

    override suspend fun loadProjectTasks(project: Project) {
        _projectDetailState.value = UiState.Loading

        try {
            when (val tasksResult = getProjectTasks(project.id)) {
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
        } catch (e: Exception) {
            _projectDetailState.value = UiState.Error(e.message ?: "Error desconocido")
        }
    }

    override suspend fun getProjectTasks(projectId: Int): Result<List<Task>> = try {
        val response = apiService.getProjectTasks(projectId)
        Result.Success(response.map { it.toDomain() })
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

    override suspend fun getProjectProgrammers(projectId: Int): Result<List<ProjectProgrammerResponse>> = try {
        val response = apiService.getProjectProgrammers(projectId)
        Result.Success(response)
    } catch (e: Exception) {
        Result.Error(e)
    }
}