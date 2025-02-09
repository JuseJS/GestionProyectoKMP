package data.network.rest

import data.network.rest.model.requests.CreateProjectRequest
import data.network.rest.model.requests.LoginRequest
import data.network.rest.model.responses.AuthResponse
import data.network.rest.model.responses.ProjectResponse

interface ApiService {
    suspend fun login(request: LoginRequest): AuthResponse
    suspend fun getProjects(): List<ProjectResponse>
    suspend fun createProject(request: CreateProjectRequest): ProjectResponse
    suspend fun getProjectDetails(id: String): ProjectResponse
}

@PublishedApi
internal class ApiServiceImpl @PublishedApi internal constructor(
    private val apiClient: ApiClient
) : ApiService {

    override suspend fun login(request: LoginRequest): AuthResponse =
        apiClient.post<AuthResponse, _>("/auth/login", request)

    override suspend fun getProjects(): List<ProjectResponse> =
        apiClient.get("/projects")

    override suspend fun createProject(request: CreateProjectRequest): ProjectResponse =
        apiClient.post<ProjectResponse, _>("/projects", request)

    override suspend fun getProjectDetails(id: String): ProjectResponse =
        apiClient.get("/projects/$id")
}