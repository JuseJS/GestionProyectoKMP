package presentation.viewmodel

import domain.common.Result
import domain.model.Project
import domain.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import presentation.common.UiState
import data.store.UserStore

data class WelcomeData(
    val activeProjects: List<Project> = emptyList(),
    val managerProjects: List<Project> = emptyList(),
    val currentUser: User? = UserStore.currentUser.value
)

class WelcomeViewModel(
    private val getActiveProjectsUseCase: GetActiveProjectsUseCase,
    private val getManagerProjectsUseCase: GetManagerProjectsUseCase,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)
) {
    private val _uiState = MutableStateFlow<UiState<WelcomeData>>(UiState.Loading)
    val uiState: StateFlow<UiState<WelcomeData>> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        scope.launch {
            _uiState.value = UiState.Loading

            try {
                // Cargar proyectos activos
                when (val activeResult = getActiveProjectsUseCase(Unit)) {
                    is Result.Success -> {
                        val activeProjects = activeResult.data

                        // Cargar proyectos del gestor
                        when (val managerResult = getManagerProjectsUseCase(Unit)) {
                            is Result.Success -> {
                                val welcomeData = WelcomeData(
                                    activeProjects = activeProjects,
                                    managerProjects = managerResult.data
                                )
                                _uiState.value = UiState.Success(welcomeData)
                            }
                            is Result.Error -> {
                                _uiState.value = UiState.Error(
                                    managerResult.exception.message ?: "Error al cargar proyectos del gestor"
                                )
                            }
                        }
                    }
                    is Result.Error -> {
                        _uiState.value = UiState.Error(
                            activeResult.exception.message ?: "Error al cargar proyectos activos"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun refresh() {
        loadData()
    }
}