package domain.repository

import domain.common.Result
import domain.model.Project
import data.network.rest.model.requests.CreateProjectRequest

interface ProjectRepository {
    suspend fun getAllProjects(): Result<List<Project>>
    suspend fun getActiveProjects(): Result<List<Project>>
    suspend fun getManagerProjects(): Result<List<Project>>
    suspend fun getProjectDetails(id: String): Result<Project>
    suspend fun createProject(request: CreateProjectRequest): Result<Project>
}