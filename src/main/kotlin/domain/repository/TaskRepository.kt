package domain.repository

import domain.common.Result
import domain.model.Task
import data.network.rest.model.requests.CreateTaskRequest
import data.network.rest.model.requests.AssignTaskRequest
import kotlinx.coroutines.flow.StateFlow
import presentation.states.ProjectDetailState
import presentation.states.TasksState
import presentation.states.UiState

interface TaskRepository {
    val projectDetailState: StateFlow<UiState<ProjectDetailState>>
    val tasksState: StateFlow<UiState<TasksState>>

    suspend fun loadAllTasks()
    suspend fun loadProjectTasks(projectId: Int)

    suspend fun getUnassignedTasks(): Result<List<Task>>
    suspend fun getAssignedTasks(): Result<List<Task>>
    suspend fun getCompletedTasks(): Result<List<Task>>
    suspend fun getProjectTasks(projectId: Int): Result<List<Task>>
    suspend fun getTaskDetails(id: Int): Result<Task>
    suspend fun createTask(request: CreateTaskRequest): Result<Task>
    suspend fun assignTask(request: AssignTaskRequest): Result<Task>
    suspend fun completeTask(taskId: Int): Result<Task>
}