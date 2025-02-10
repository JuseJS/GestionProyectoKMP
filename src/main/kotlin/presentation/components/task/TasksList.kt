package presentation.components.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.model.Programmer
import domain.model.Task
import kotlin.collections.forEach

@Composable
fun TasksList(
    tasks: List<Task>,
    programmers: List<Programmer>,
    onTaskClick: (Task) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        tasks.forEach { task ->
            TaskCard(
                task = task,
                programmers = programmers,
                onClick = onTaskClick
            )
        }
    }
}