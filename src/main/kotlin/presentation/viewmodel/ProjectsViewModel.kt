package presentation.viewmodel

import domain.common.Result
import domain.model.Project
import domain.usecase.project.GetProjectsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import presentation.common.UiState

class ProjectsViewModel(
    private val getProjectsUseCase: GetProjectsUseCase,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)
) {
    private val _uiState = MutableStateFlow<UiState<List<Project>>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        loadProjects()
    }

    private fun loadProjects() {
        scope.launch {
            _uiState.value = UiState.Loading
            when (val result = getProjectsUseCase(Unit)) {
                is Result.Success -> _uiState.value = UiState.Success(result.data)
                is Result.Error -> _uiState.value = UiState.Error(result.exception.message ?: "Unknown error")
            }
        }
    }
}