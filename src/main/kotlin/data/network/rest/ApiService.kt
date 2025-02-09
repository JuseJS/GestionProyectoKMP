package data.network.rest

import data.network.rest.model.requests.CreateProjectRequest
import data.network.rest.model.requests.LoginRequest
import data.network.rest.model.responses.AuthResponse
import data.network.rest.model.responses.ProjectResponse

interface ApiService {
    // Login
    suspend fun login(request: LoginRequest): AuthResponse
    // Proyectos
    suspend fun getAllProjects(): List<ProjectResponse>
    suspend fun getActiveProjects(): List<ProjectResponse>
    suspend fun getManagerProjects(managerId: Int): List<ProjectResponse>
    suspend fun getProjectDetails(id: String): ProjectResponse
    suspend fun createProject(request: CreateProjectRequest): ProjectResponse
}

@PublishedApi
internal class ApiServiceImpl @PublishedApi internal constructor(
    private val apiClient: ApiClient
) : ApiService {

    override suspend fun login(request: LoginRequest): AuthResponse =
        apiClient.post<AuthResponse, _>("/auth/login", request)

    override suspend fun getAllProjects(): List<ProjectResponse> =
        apiClient.get("/proyectos")

    override suspend fun getActiveProjects(): List<ProjectResponse> =
        apiClient.get("/proyectos/activos")

    override suspend fun getManagerProjects(managerId: Int): List<ProjectResponse> =
        apiClient.post<List<ProjectResponse>, Map<String, Int>>("/proyectos/gestor", mapOf("id" to managerId))

    override suspend fun getProjectDetails(id: String): ProjectResponse =
        apiClient.get("/proyectos/$id")

    override suspend fun createProject(request: CreateProjectRequest): ProjectResponse =
        apiClient.post<ProjectResponse, _>("/proyectos", request)
}