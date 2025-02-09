package presentation.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import presentation.states.UiState
import domain.repository.ProjectRepository
import presentation.states.ProjectsState

class WelcomeViewModel(
    private val projectRepository: ProjectRepository,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)
) {
    val uiState: StateFlow<UiState<ProjectsState>> = projectRepository.projectsState

    init {
        loadData()
    }

    fun loadData() {
        scope.launch {
            projectRepository.loadProjects()
        }
    }

    fun refresh() {
        loadData()
    }
}