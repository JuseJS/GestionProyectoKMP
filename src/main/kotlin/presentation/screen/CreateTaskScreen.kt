package presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import data.store.UserStore
import domain.model.Project
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.components.BackButton
import presentation.components.BaseScreen
import presentation.components.ContentCard
import presentation.theme.Theme
import presentation.viewmodel.ProjectDetailViewModel
import data.network.rest.model.responses.ProjectProgrammerResponse

class CreateTaskScreen(private val project: Project) : Screen, KoinComponent {
    private val viewModel: ProjectDetailViewModel by inject()

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        var selectedItem by remember { mutableStateOf(1) }
        val currentUser by UserStore.currentUser.collectAsState()

        var name by remember { mutableStateOf("") }
        var description by remember { mutableStateOf("") }
        var estimation by remember { mutableStateOf("") }
        var isError by remember { mutableStateOf(false) }
        var errorMessage by remember { mutableStateOf("") }
        var programmers by remember { mutableStateOf<List<ProjectProgrammerResponse>>(emptyList()) }
        var selectedProgrammerId by remember { mutableStateOf<Int?>(null) }
        var expandedDropdown by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            programmers = viewModel.getProjectProgrammers(project.id)
        }

        BaseScreen(
            selectedItem = selectedItem,
            onItemSelected = { selectedItem = it },
            navigator = navigator
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                BackButton(
                    text = "Volver al Proyecto",
                    onClick = { navigator?.pop() }
                )

                ContentCard {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Nueva Tarea",
                            style = MaterialTheme.typography.h4,
                            color = Theme.materialColors.onBackground
                        )

                        OutlinedTextField(
                            value = name,
                            onValueChange = {
                                name = it
                                isError = false
                            },
                            label = { Text("Nombre de la tarea") },
                            modifier = Modifier.fillMaxWidth(),
                            isError = isError
                        )

                        OutlinedTextField(
                            value = description,
                            onValueChange = {
                                description = it
                                isError = false
                            },
                            label = { Text("Descripción") },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 3,
                            maxLines = 5,
                            isError = isError
                        )

                        Box(modifier = Modifier.fillMaxWidth()) {
                            OutlinedTextField(
                                value = programmers.find { it.id == selectedProgrammerId }?.name ?: "Sin asignar",
                                onValueChange = { },
                                readOnly = true,
                                label = { Text("Programador") },
                                trailingIcon = {
                                    IconButton(onClick = { expandedDropdown = !expandedDropdown }) {
                                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDropdown)
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Theme.materialColors.primary,
                                    unfocusedBorderColor = Theme.colors.outline
                                )
                            )

                            DropdownMenu(
                                expanded = expandedDropdown,
                                onDismissRequest = { expandedDropdown = false },
                                modifier = Modifier.fillMaxWidth(0.9f)
                            ) {
                                DropdownMenuItem(
                                    onClick = {
                                        selectedProgrammerId = null
                                        expandedDropdown = false
                                    }
                                ) {
                                    Text("Sin asignar")
                                }
                                programmers.forEach { programmer ->
                                    DropdownMenuItem(
                                        onClick = {
                                            selectedProgrammerId = programmer.id
                                            expandedDropdown = false
                                        }
                                    ) {
                                        Text(programmer.name)
                                    }
                                }
                            }
                        }

                        OutlinedTextField(
                            value = estimation,
                            onValueChange = {
                                if (it.isEmpty() || it.all { char -> char.isDigit() }) {
                                    estimation = it
                                    isError = false
                                }
                            },
                            label = { Text("Estimación (horas)") },
                            modifier = Modifier.fillMaxWidth(),
                            isError = isError
                        )

                        if (isError) {
                            Text(
                                text = errorMessage,
                                color = Theme.materialColors.error,
                                style = MaterialTheme.typography.caption
                            )
                        }

                        Button(
                            onClick = {
                                if (name.isBlank() || description.isBlank() || estimation.isBlank()) {
                                    isError = true
                                    errorMessage = "Todos los campos son obligatorios"
                                    return@Button
                                }

                                val estimationInt = estimation.toIntOrNull()
                                if (estimationInt == null || estimationInt <= 0) {
                                    isError = true
                                    errorMessage = "La estimación debe ser un número válido mayor que 0"
                                    return@Button
                                }

                                currentUser?.let { user ->
                                    viewModel.createTask(
                                        name = name,
                                        description = description,
                                        estimation = estimationInt,
                                        programmerId = selectedProgrammerId
                                    )
                                    navigator?.pop()
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Theme.materialColors.primary,
                                contentColor = Theme.materialColors.onPrimary
                            )
                        ) {
                            Text("Crear Tarea")
                        }
                    }
                }
            }
        }
    }
}