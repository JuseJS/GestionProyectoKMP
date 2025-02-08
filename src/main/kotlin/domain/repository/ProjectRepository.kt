package domain.repository

import domain.common.Result
import domain.model.Project
import data.network.rest.model.requests.CreateProjectRequest

interface ProjectRepository {
    suspend fun getProjects(): Result<List<Project>>
    suspend fun createProject(request: CreateProjectRequest): Result<Project>
    suspend fun getProjectDetails(id: String): Result<Project>
}