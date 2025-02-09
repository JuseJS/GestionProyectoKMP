package data.repository

import data.network.rest.ApiService
import data.network.rest.model.mappers.toDomain
import data.network.rest.model.requests.CreateProjectRequest
import data.store.UserStore
import domain.common.Result
import domain.model.Project
import domain.repository.ProjectRepository

class ProjectRepositoryImpl(
    private val apiService: ApiService
) : ProjectRepository {

    override suspend fun getAllProjects(): Result<List<Project>> = try {
        val response = apiService.getAllProjects()
        Result.Success(response.map { it.toDomain() })
    } catch (e: Exception) {
        Result.Error(e)
    }

    override suspend fun getActiveProjects(): Result<List<Project>> = try {
        val response = apiService.getActiveProjects()
        Result.Success(response.map { it.toDomain() })
    } catch (e: Exception) {
        Result.Error(e)
    }

    override suspend fun getManagerProjects(): Result<List<Project>> = try {
        val currentUser = UserStore.currentUser.value
            ?: throw IllegalStateException("Usuario no autenticado")

        val response = apiService.getManagerProjects(currentUser.id)
        Result.Success(response.map { it.toDomain() })
    } catch (e: Exception) {
        Result.Error(e)
    }

    override suspend fun getProjectDetails(id: String): Result<Project> = try {
        val response = apiService.getProjectDetails(id)
        Result.Success(response.toDomain())
    } catch (e: Exception) {
        Result.Error(e)
    }

    override suspend fun createProject(request: CreateProjectRequest): Result<Project> = try {
        val response = apiService.createProject(request)
        Result.Success(response.toDomain())
    } catch (e: Exception) {
        Result.Error(e)
    }
}