package data.network.rest

import data.network.rest.model.requests.AssignProgrammerRequest
import data.network.rest.model.requests.AssignTaskRequest
import data.network.rest.model.requests.CreateProjectRequest
import data.network.rest.model.requests.CreateTaskRequest
import data.network.rest.model.requests.LoginRequest
import data.network.rest.model.responses.AssignProgrammerResponse
import data.network.rest.model.responses.AuthResponse
import data.network.rest.model.responses.ProjectResponse
import data.network.rest.model.responses.TaskResponse
import kotlinx.serialization.json.Json

interface ApiService {
    // Login
    suspend fun login(request: LoginRequest): AuthResponse
    // Proyectos
    suspend fun getActiveProjects(): List<ProjectResponse>
    suspend fun getEndedProjects(): List<ProjectResponse>
    suspend fun getManagerActiveProjects(managerId: Int): List<ProjectResponse>
    suspend fun getManagerEndedProjects(managerId: Int): List<ProjectResponse>
    suspend fun createProject(request: CreateProjectRequest): ProjectResponse
    suspend fun assignProgrammerToProject(request: AssignProgrammerRequest): AssignProgrammerResponse

    // Tareas
    suspend fun getProjectTasks(projectId: Int): List<TaskResponse>
    suspend fun createTask(request: CreateTaskRequest): TaskResponse
    suspend fun assignTask(request: AssignTaskRequest): TaskResponse
}

@PublishedApi
internal class ApiServiceImpl @PublishedApi internal constructor(
    private val apiClient: ApiClient
) : ApiService {

    override suspend fun login(request: LoginRequest): AuthResponse =
        apiClient.post<AuthResponse, _>("/auth/login", request)

    override suspend fun assignProgrammerToProject(request: AssignProgrammerRequest): AssignProgrammerResponse =
        apiClient.post<AssignProgrammerResponse, _>("/proyectos/asignar-programador", request)

    override suspend fun getActiveProjects(): List<ProjectResponse> =
        apiClient.get("/proyectos/activos")

    override suspend fun getEndedProjects(): List<ProjectResponse> =
        apiClient.get("/proyectos/finalizados")

    override suspend fun getManagerActiveProjects(managerId: Int): List<ProjectResponse> {
        val response = apiClient.post<String, Map<String, Int>>(
            "/proyectos/gestor/activos",
            mapOf("id" to managerId)
        )
        println("Raw response: $response")
        return Json.decodeFromString<List<ProjectResponse>>(response)
    }

    override suspend fun getManagerEndedProjects(managerId: Int): List<ProjectResponse> {
        val response = apiClient.post<String, Map<String, Int>>(
            "/proyectos/gestor/finalizados",
            mapOf("id" to managerId)
        )
        println("Raw response: $response")
        return Json.decodeFromString<List<ProjectResponse>>(response)
    }

    override suspend fun createProject(request: CreateProjectRequest): ProjectResponse =
        apiClient.post<ProjectResponse, _>("/proyectos", request)

    override suspend fun getProjectTasks(projectId: Int): List<TaskResponse> {
        return apiClient.get("/proyectos/$projectId/tareas")
    }

    override suspend fun createTask(request: CreateTaskRequest): TaskResponse {
        return apiClient.post("/tareas/crear", request)
    }

    override suspend fun assignTask(request: AssignTaskRequest): TaskResponse =
        apiClient.post("/tareas/asignar-programador", request)
}