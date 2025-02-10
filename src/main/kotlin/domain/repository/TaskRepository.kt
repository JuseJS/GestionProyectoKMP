package domain.repository

import data.network.rest.model.requests.AssignTaskRequest
import data.network.rest.model.requests.CreateTaskRequest
import data.network.rest.model.responses.ProjectProgrammerResponse
import domain.common.Result
import domain.model.Project
import domain.model.Task
import kotlinx.coroutines.flow.StateFlow
import presentation.states.ProjectDetailState
import presentation.states.UiState

interface TaskRepository {
    val projectDetailState: StateFlow<UiState<ProjectDetailState>>

    suspend fun loadProjectTasks(project: Project)
    suspend fun getProjectTasks(projectId: Int): Result<List<Task>>
    suspend fun getProjectProgrammers(projectId: Int): Result<List<ProjectProgrammerResponse>>
    suspend fun createTask(request: CreateTaskRequest): Result<Task>
    suspend fun assignTask(request: AssignTaskRequest): Result<Task>
}