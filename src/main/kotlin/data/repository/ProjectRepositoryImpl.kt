package data.repository

import data.network.rest.ApiService
import data.network.rest.model.requests.CreateProjectRequest
import domain.common.Result
import domain.model.Project
import domain.repository.ProjectRepository
import data.network.rest.model.toDomain

class ProjectRepositoryImpl(
    private val apiService: ApiService
) : ProjectRepository {
    override suspend fun getProjects(): Result<List<Project>> = try {
        Result.Success(apiService.getProjects().map { it.toDomain() })
    } catch (e: Exception) {
        Result.Error(e)
    }

    override suspend fun createProject(request: CreateProjectRequest): Result<Project> = try {
        Result.Success(apiService.createProject(request).toDomain())
    } catch (e: Exception) {
        Result.Error(e)
    }

    override suspend fun getProjectDetails(id: String): Result<Project> = try {
        Result.Success(apiService.getProjectDetails(id).toDomain())
    } catch (e: Exception) {
        Result.Error(e)
    }
}