package domain.repository

import data.network.rest.model.requests.AssignProgrammerRequest
import data.network.rest.model.requests.AssignTaskRequest
import domain.common.Result
import domain.model.Project
import data.network.rest.model.requests.CreateProjectRequest
import domain.model.Task
import kotlinx.coroutines.flow.StateFlow
import presentation.states.ProjectsState
import presentation.states.UiState

interface ProjectRepository {
    val projectsState: StateFlow<UiState<ProjectsState>>

    suspend fun loadProjects()

    suspend fun getEndedProjects(): Result<List<Project>>
    suspend fun getActiveProjects(): Result<List<Project>>
    suspend fun getManagerActiveProjects(): Result<List<Project>>
    suspend fun getManagerEndedProjects(): Result<List<Project>>
    suspend fun assignProgrammer(request: AssignProgrammerRequest): Result<Unit>
    suspend fun createProject(request: CreateProjectRequest): Result<Project>
}