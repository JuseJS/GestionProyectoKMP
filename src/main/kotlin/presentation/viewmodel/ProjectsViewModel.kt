package presentation.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import presentation.states.UiState
import domain.repository.ProjectRepository
import presentation.states.ProjectsState

class ProjectViewModel(
    private val projectRepository: ProjectRepository,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)
) {
    private val _showOnlyManagerProjects = MutableStateFlow(false)
    val showOnlyManagerProjects: StateFlow<Boolean> = _showOnlyManagerProjects.asStateFlow()

    val uiState: StateFlow<UiState<ProjectsState>> = projectRepository.projectsState

    init {
        loadData()
    }

    fun loadData() {
        scope.launch {
            projectRepository.loadProjects()
        }
    }

    fun toggleFilter() {
        _showOnlyManagerProjects.value = !_showOnlyManagerProjects.value
    }

    fun refresh() {
        loadData()
    }
}