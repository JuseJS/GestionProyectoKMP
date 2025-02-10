package presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import domain.model.Task
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.components.BackButton
import presentation.components.BaseScreen
import presentation.components.project.AssignProgrammerDialog
import presentation.components.task.TaskDetailsCard
import presentation.viewmodel.TaskDetailViewModel

class TaskDetailScreen(private val task: Task) : Screen, KoinComponent {
    private val viewModel: TaskDetailViewModel by inject()

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        var selectedItem by remember { mutableStateOf(1) }
        var showAssignDialog by remember { mutableStateOf(false) }

        val availableProgrammers by viewModel.availableProgrammers.collectAsState()
        val currentTask by viewModel.currentTask.collectAsState()
        val currentProgrammer by viewModel.currentProgrammer.collectAsState()

        LaunchedEffect(task) {
            viewModel.initTask(task)
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

                currentTask?.let { task ->
                    TaskDetailsCard(
                        task = task,
                        programmer = currentProgrammer,
                        onAssignClick = { showAssignDialog = true }
                    )
                }
            }

            if (showAssignDialog) {
                AssignProgrammerDialog(
                    availableProgrammers = availableProgrammers,
                    onProgrammerSelected = { programmerId ->
                        viewModel.assignProgrammer(programmerId)
                        showAssignDialog = false
                    },
                    onDismiss = { showAssignDialog = false }
                )
            }
        }
    }
}