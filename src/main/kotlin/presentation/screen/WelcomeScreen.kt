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
import domain.model.User
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.states.UiState
import presentation.components.*
import presentation.components.common.ErrorMessage
import presentation.components.common.LoadingScreen
import presentation.components.project.EmptyProjectsMessage
import presentation.viewmodel.WelcomeViewModel
import data.store.UserStore
import presentation.components.project.ProjectsList

class WelcomeScreen : Screen, KoinComponent {
    private val viewModel: WelcomeViewModel by inject()

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val uiState by viewModel.uiState.collectAsState()
        val currentUser by UserStore.currentUser.collectAsState()
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
                        item {
                            HeaderSection(
                                title = "Bienvenido",
                                content = {
                                    UserInfoCard(user = currentUser)
                                }
                            )
                        }

                        if (state.data.managerActiveProjects.isEmpty() &&
                            state.data.managerEndedProjects.isEmpty()) {
                            item {
                                EmptyProjectsMessage()
                            }
                        } else {
                            // Proyectos Activos
                            if (state.data.managerActiveProjects.isNotEmpty()) {
                                item {
                                    ContentSection(title = "Tus Proyectos Activos") {
                                        ProjectsList(
                                            projects = state.data.managerActiveProjects,
                                            onProjectClick = { project ->
                                                navigator?.push(ProjectDetailScreen(project))
                                            }
                                        )
                                    }
                                }
                            }

                            // Proyectos Finalizados
                            if (state.data.managerEndedProjects.isNotEmpty()) {
                                item {
                                    ContentSection(title = "Tus Proyectos Finalizados") {
                                        ProjectsList(
                                            projects = state.data.managerEndedProjects,
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
                label = user?.role ?: "Sin Rol",
                value = user?.name ?: "Usuario",
            )
        }
    }
}