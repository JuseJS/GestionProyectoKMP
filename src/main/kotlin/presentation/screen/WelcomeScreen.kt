package presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import domain.model.Project
import domain.model.User
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.common.UiState
import presentation.components.*
import presentation.components.common.ErrorMessage
import presentation.components.common.LoadingScreen
import presentation.components.project.EmptyProjectsMessage
import presentation.theme.Theme
import presentation.viewmodel.WelcomeViewModel

class WelcomeScreen : Screen, KoinComponent {
    private val viewModel: WelcomeViewModel by inject()

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val uiState by viewModel.uiState.collectAsState()
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
                        // Header Section
                        item {
                            HeaderSection(
                                title = "Bienvenido",
                                content = {
                                    UserInfoCard(user = state.data.currentUser)
                                }
                            )
                        }

                        if (state.data.activeProjects.isEmpty() &&
                            state.data.managerProjects.isEmpty()) {
                            item {
                                EmptyProjectsMessage()
                            }
                        } else {
                            // Active Projects Section
                            if (state.data.activeProjects.isNotEmpty()) {
                                item {
                                    ContentSection(title = "Proyectos Activos") {
                                        ProjectsList(
                                            projects = state.data.activeProjects,
                                            onProjectClick = { project ->
                                                navigator?.push(ProjectDetailScreen(project))
                                            }
                                        )
                                    }
                                }
                            }

                            // Manager's Projects Section
                            if (state.data.managerProjects.isNotEmpty()) {
                                item {
                                    ContentSection(title = "Mis Proyectos") {
                                        ProjectsList(
                                            projects = state.data.managerProjects,
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
    }
}

@Composable
private fun UserInfoCard(user: User?) {
    ContentCard {
        Row(
            modifier = Modifier.padding(24.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            InfoItem(
                icon = Icons.Default.Person,
                label = "Usuario",
                value = user?.name ?: "Usuario",
            )
        }
    }
}

@Composable
private fun ProjectsList(
    projects: List<Project>,
    onProjectClick: (Project) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        projects.forEach { project ->
            ProjectCard(
                project = project,
                onClick = onProjectClick
            )
        }
    }
}