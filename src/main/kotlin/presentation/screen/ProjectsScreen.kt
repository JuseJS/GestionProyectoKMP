package presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.states.UiState
import presentation.components.*
import presentation.components.common.ErrorMessage
import presentation.components.common.LoadingScreen
import presentation.components.project.EmptyProjectsMessage
import presentation.components.project.FilterChip
import presentation.components.project.ProjectsList
import presentation.viewmodel.ProjectViewModel

class ProjectsScreen : Screen, KoinComponent {
    private val viewModel: ProjectViewModel by inject()

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val uiState by viewModel.uiState.collectAsState()
        val showOnlyManagerProjects by viewModel.showOnlyManagerProjects.collectAsState()
        var selectedItem by remember { mutableStateOf(0) }

        BaseScreen(
            selectedItem = selectedItem,
            onItemSelected = { selectedItem = it },
            navigator = navigator
        ) {
            when (val state = uiState) {
                is UiState.Loading -> LoadingScreen()
                is UiState.Error -> ErrorMessage(
                    message = state.message,
                    onRetry = { viewModel.refresh() }
                )
                is UiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        // Filter Section
                        item {
                            HeaderSection(
                                title = "Proyectos",
                                content = {
                                    FilterChip(
                                        selected = showOnlyManagerProjects,
                                        onClick = { viewModel.toggleFilter() }
                                    )
                                }
                            )
                        }

                        // Projects List
                        val projectsToShow = if (showOnlyManagerProjects) {
                            state.data.managerActiveProjects
                        } else {
                            state.data.activeProjects
                        }

                        if (projectsToShow.isEmpty()) {
                            item {
                                EmptyProjectsMessage()
                            }
                        } else {
                            item {
                                ProjectsList(
                                    projects = projectsToShow,
                                    onProjectClick = { project ->
                                        navigator?.push(ProjectDetailScreen(project))
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}