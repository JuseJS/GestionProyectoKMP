package presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import domain.model.TaskData
import presentation.components.SidebarMenu
import presentation.components.task.TaskDetailsCard
import presentation.theme.Theme

class TaskDetailScreen(private val task: TaskData) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        var selectedItem by remember { mutableStateOf(1) }
        var showAssignDialog by remember { mutableStateOf(false) }
        var selectedDeveloper by remember { mutableStateOf(task.assignedDeveloper) }

        // Lista de ejemplo de programadores disponibles
        val availableDevelopers = remember {
            listOf(
                "Ana García",
                "Carlos Rodríguez",
                "María López",
                "Juan Pérez",
                "Laura Torres"
            )
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Theme.materialColors.background
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                // SideMenu
                SidebarMenu(
                    selectedItem = selectedItem,
                    onItemSelected = { selectedItem = it },
                    onLogout = { navigator?.pop() },
                    navigator = navigator
                )

                // Contenido principal
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(24.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        // Botón de retorno
                        Button(
                            onClick = { navigator?.pop() },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Theme.colors.surfaceContainer,
                                contentColor = Theme.materialColors.primary
                            ),
                            elevation = ButtonDefaults.elevation(
                                defaultElevation = 0.dp,
                                pressedElevation = 0.dp
                            ),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.padding(bottom = 16.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Volver",
                                    tint = Theme.materialColors.primary
                                )
                                Text(
                                    text = "Volver a Proyecto",
                                    color = Theme.materialColors.primary
                                )
                            }
                        }

                        // Detalles de la tarea
                        TaskDetailsCard(
                            task = task,
                            selectedDeveloper = selectedDeveloper,
                            onAssignClick = { showAssignDialog = true }
                        )
                    }
                }
            }
        }

        // Diálogo para asignar programador
        if (showAssignDialog) {
            AlertDialog(
                onDismissRequest = { showAssignDialog = false },
                title = {
                    Text(
                        "Asignar Programador",
                        style = MaterialTheme.typography.h6,
                        color = Theme.materialColors.onBackground
                    )
                },
                text = {
                    Column {
                        availableDevelopers.forEach { developer ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .clickable {
                                        selectedDeveloper = developer
                                        showAssignDialog = false
                                    },
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                RadioButton(
                                    selected = developer == selectedDeveloper,
                                    onClick = {
                                        selectedDeveloper = developer
                                        showAssignDialog = false
                                    },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = Theme.materialColors.primary
                                    )
                                )
                                Text(
                                    text = developer,
                                    color = Theme.materialColors.onBackground
                                )
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showAssignDialog = false }) {
                        Text(
                            "Cancelar",
                            color = Theme.materialColors.primary
                        )
                    }
                },
                backgroundColor = Theme.colors.surfaceContainer,
                shape = RoundedCornerShape(16.dp)
            )
        }
    }
}