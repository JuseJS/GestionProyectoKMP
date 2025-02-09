package presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import domain.model.Project
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.states.UiState
import presentation.components.*
import presentation.components.common.ErrorMessage
import presentation.components.common.LoadingScreen
import presentation.components.task.EmptyTasksMessage
import presentation.components.task.TasksList
import presentation.viewmodel.ProjectDetailViewModel

class ProjectDetailScreen(private val project: Project) : Screen, KoinComponent {
    private val viewModel: ProjectDetailViewModel by inject()

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val uiState by viewModel.uiState.collectAsState()
        var selectedItem by remember { mutableStateOf(1) }

        // Inicializar el viewModel con el proyecto actual
        LaunchedEffect(project) {
            viewModel.initProject(project)
        }

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
                        // Back Button
                        item {
                            BackButton(
                                text = "Volver a Proyectos",
                                onClick = { navigator?.pop() }
                            )
                        }

                        // Project Details Card
                        item {
                            ProjectCard(
                                project = project,
                                isDetailView = true
                            )
                        }

                        // Tasks Section Header
                        item {
                            HeaderSection(
                                title = "Tareas del Proyecto",
                                content = {
                                    Button(
                                        onClick = {
                                            // TODO: Implementar navegación a pantalla de crear tarea
                                        }
                                    ) {
                                        Text("Nueva Tarea")
                                    }
                                }
                            )
                        }

                        // Tasks List
                        item {
                            if (state.data.tasks.isEmpty()) {
                                EmptyTasksMessage()
                            } else {
                                TasksList(
                                    tasks = state.data.tasks,
                                    onTaskClick = { task ->
                                        // TODO: Implementar navegación a detalle de tarea
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