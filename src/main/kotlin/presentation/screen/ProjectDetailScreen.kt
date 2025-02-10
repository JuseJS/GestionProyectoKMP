package presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import domain.model.Project
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.components.*
import presentation.components.common.ErrorMessage
import presentation.components.common.LoadingScreen
import presentation.components.project.AssignProgrammerDialog
import presentation.components.task.EmptyTasksMessage
import presentation.components.task.TasksList
import presentation.states.UiState
import presentation.theme.Theme
import presentation.viewmodel.ProjectDetailViewModel

class ProjectDetailScreen(private val project: Project) : Screen, KoinComponent {
    private val viewModel: ProjectDetailViewModel by inject()

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val uiState by viewModel.uiState.collectAsState()
        val projectProgrammers by viewModel.projectProgrammers.collectAsState()
        val availableProgrammers by viewModel.availableProgrammers.collectAsState()
        var selectedItem by remember { mutableStateOf(1) }
        var showAssignProgrammerDialog by remember { mutableStateOf(false) }

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
                        item {
                            BackButton(
                                text = "Volver a Proyectos",
                                onClick = { navigator?.pop() }
                            )
                        }

                        item {
                            ProjectCard(
                                project = project,
                                isDetailView = true
                            )
                        }

                        // Programadores Section
                        item {
                            ContentSection(
                                title = "Programadores Asignados",
                                actions = {
                                    Button(
                                        onClick = { showAssignProgrammerDialog = true },
                                        colors = ButtonDefaults.buttonColors(
                                            backgroundColor = Theme.materialColors.primary,
                                            contentColor = Theme.materialColors.onPrimary
                                        )
                                    ) {
                                        Text("Añadir Programador")
                                    }
                                }
                            ) {
                                if (projectProgrammers.isEmpty()) {
                                    Text(
                                        text = "No hay programadores asignados",
                                        style = MaterialTheme.typography.body1,
                                        color = Theme.colors.textSecondary
                                    )
                                } else {
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        projectProgrammers.forEach { programmer ->
                                            Card(
                                                backgroundColor = Theme.colors.surfaceContainerHigh,
                                                elevation = 0.dp,
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                Row(
                                                    modifier = Modifier.padding(16.dp),
                                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.Person,
                                                        contentDescription = null,
                                                        tint = Theme.materialColors.primary
                                                    )
                                                    Text(
                                                        text = programmer.name,
                                                        color = Theme.materialColors.onBackground
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        item {
                            ContentSection(
                                title = "Tareas del Proyecto",
                                actions = {
                                    Button(
                                        onClick = { navigator?.push(CreateTaskScreen(project)) },
                                        colors = ButtonDefaults.buttonColors(
                                            backgroundColor = Theme.materialColors.primary,
                                            contentColor = Theme.materialColors.onPrimary
                                        )
                                    ) {
                                        Text("Nueva Tarea")
                                    }
                                }
                            ) {
                                if (state.data.tasks.isEmpty()) {
                                    EmptyTasksMessage()
                                } else {
                                    TasksList(
                                        tasks = state.data.tasks,
                                        onTaskClick = { task ->
                                            navigator?.push(TaskDetailScreen(task))
                                        }
                                    )
                                }
                            }
                        }
                    }

                    if (showAssignProgrammerDialog) {
                        AssignProgrammerDialog(
                            availableProgrammers = availableProgrammers,
                            onProgrammerSelected = { programmerId ->
                                viewModel.assignProgrammerToProject(programmerId)
                            },
                            onDismiss = { showAssignProgrammerDialog = false }
                        )
                    }
                }
            }
        }
    }
}