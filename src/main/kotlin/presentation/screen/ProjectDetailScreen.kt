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
import domain.model.TaskData
import presentation.components.*
import presentation.components.task.TaskCard
import presentation.theme.Theme
import java.time.LocalDate

class ProjectDetailScreen(private val project: ProjectData) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        var selectedItem by remember { mutableStateOf(1) }

        // Datos de ejemplo de tareas
        val tasks = remember {
            listOf(
                TaskData(
                    id = 1,
                    name = "Diseño de Base de Datos",
                    description = "Crear el esquema de la base de datos para el sistema",
                    estimatedHours = 20,
                    creationDate = LocalDate.of(2024, 1, 15),
                    projectId = project.id,
                    assignedDeveloper = "Ana García",
                    completionDate = LocalDate.of(2024, 1, 20)
                ),
                TaskData(
                    id = 2,
                    name = "Implementación de API REST",
                    description = "Desarrollar endpoints para gestión de inventario",
                    estimatedHours = 40,
                    creationDate = LocalDate.of(2024, 1, 16),
                    projectId = project.id,
                    assignedDeveloper = "Carlos Rodríguez"
                ),
                TaskData(
                    id = 3,
                    name = "Desarrollo de UI",
                    description = "Crear interfaces de usuario según diseños aprobados",
                    estimatedHours = 60,
                    creationDate = LocalDate.of(2024, 1, 17),
                    projectId = project.id
                )
            )
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Theme.materialColors.background
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                SidebarMenu(
                    selectedItem = selectedItem,
                    onItemSelected = { selectedItem = it },
                    onLogout = { navigator?.pop() },
                    navigator = navigator
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(24.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        BackButton(
                            text = "Volver a Proyectos",
                            onClick = { navigator?.pop() }
                        )

                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(24.dp)
                        ) {
                            item {
                                ProjectDetailsCard(project)
                            }

                            item {
                                TasksSection(
                                    tasks = tasks,
                                    onTaskClick = { task ->
                                        navigator?.push(TaskDetailScreen(task))
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun ProjectDetailsCard(project: ProjectData) {
        ContentCard {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = project.name,
                    style = MaterialTheme.typography.h4,
                    color = Theme.materialColors.onBackground
                )

                Text(
                    text = project.description,
                    style = MaterialTheme.typography.body1,
                    color = Theme.colors.textSecondary
                )

                Divider(color = Theme.colors.outline)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    InfoItem(
                        icon = Icons.Default.Business,
                        label = "Cliente",
                        value = project.clientCompany
                    )

                    InfoItem(
                        icon = Icons.Default.DateRange,
                        label = "Fecha de inicio",
                        value = project.startDate.toString()
                    )

                    InfoItem(
                        icon = Icons.Default.Person,
                        label = "Estado",
                        value = if (project.endDate != null) "Finalizado" else "En progreso"
                    )
                }
            }
        }
    }

    @Composable
    private fun TasksSection(
        tasks: List<TaskData>,
        onTaskClick: (TaskData) -> Unit
    ) {
        ContentCard {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Tareas del Proyecto",
                    style = MaterialTheme.typography.h5,
                    color = Theme.materialColors.onBackground
                )

                tasks.forEach { task ->
                    TaskCard(
                        task = task,
                        onClick = onTaskClick
                    )
                    if (task != tasks.last()) {
                        Divider(color = Theme.colors.outline)
                    }
                }
            }
        }
    }
}