// data/repository/ProjectRepositoryImpl.kt
package data.repository

import data.network.rest.ApiService
import data.network.rest.model.mappers.toDomain
import data.network.rest.model.requests.AssignProgrammerRequest
import data.network.rest.model.requests.CreateProjectRequest
import data.store.UserStore
import domain.common.Result
import domain.model.Project
import domain.repository.ProjectRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import presentation.states.ProjectsState
import presentation.states.UiState

class ProjectRepositoryImpl(
    private val apiService: ApiService
) : ProjectRepository {

    private val _projectsState = MutableStateFlow<UiState<ProjectsState>>(UiState.Loading)
    override val projectsState: StateFlow<UiState<ProjectsState>> = _projectsState.asStateFlow()

    override suspend fun loadProjects() {
        _projectsState.value = UiState.Loading

        try {
            // Cargar proyectos activos
            when (val activeResult = getActiveProjects()) {
                is Result.Success -> {
                    val activeProjects = activeResult.data

                    // Cargar proyectos activos del gestor
                    when (val managerActiveResult = getManagerActiveProjects()) {
                        is Result.Success -> {
                            val managerActiveProjects = managerActiveResult.data

                            // Cargar proyectos finalizados del gestor
                            when (val managerEndedResult = getManagerEndedProjects()) {
                                is Result.Success -> {
                                    _projectsState.value = UiState.Success(
                                        ProjectsState(
                                            activeProjects = activeProjects,
                                            managerActiveProjects = managerActiveProjects,
                                            managerEndedProjects = managerEndedResult.data
                                        )
                                    )
                                }
                                is Result.Error -> {
                                    _projectsState.value = UiState.Error(
                                        managerEndedResult.exception.message ?:
                                        "Error al cargar proyectos finalizados del gestor"
                                    )
                                }
                            }
                        }
                        is Result.Error -> {
                            _projectsState.value = UiState.Error(
                                managerActiveResult.exception.message ?:
                                "Error al cargar proyectos activos del gestor"
                            )
                        }
                    }
                }
                is Result.Error -> {
                    _projectsState.value = UiState.Error(
                        activeResult.exception.message ?: "Error al cargar proyectos activos"
                    )
                }
            }
        } catch (e: Exception) {
            _projectsState.value = UiState.Error(e.message ?: "Error desconocido")
        }
    }

    override suspend fun getActiveProjects(): Result<List<Project>> = try {
        val response = apiService.getActiveProjects()
        Result.Success(response.map { it.toDomain() })
    } catch (e: Exception) {
        Result.Error(e)
    }

    override suspend fun getEndedProjects(): Result<List<Project>> = try {
        val response = apiService.getEndedProjects()
        Result.Success(response.map { it.toDomain() })
    } catch (e: Exception) {
        Result.Error(e)
    }

    override suspend fun getManagerActiveProjects(): Result<List<Project>> = try {
        val currentUser = UserStore.currentUser.value
            ?: throw IllegalStateException("Usuario no autenticado")

        val response = apiService.getManagerActiveProjects(currentUser.id)
        Result.Success(response.map { it.toDomain() })
    } catch (e: Exception) {
        Result.Error(e)
    }

    override suspend fun getManagerEndedProjects(): Result<List<Project>> = try {
        val currentUser = UserStore.currentUser.value
            ?: throw IllegalStateException("Usuario no autenticado")

        val response = apiService.getManagerEndedProjects(currentUser.id)
        Result.Success(response.map { it.toDomain() })
    } catch (e: Exception) {
        Result.Error(e)
    }

    override suspend fun assignProgrammer(request: AssignProgrammerRequest): Result<Unit> = try {
        val response = apiService.assignProgrammerToProject(request)
        Result.Success(Unit)
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