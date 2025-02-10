package presentation.components.task

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.model.Programmer
import domain.model.Task
import presentation.components.InfoItem
import presentation.components.StatusChip
import presentation.theme.Theme

@Composable
fun TaskCard(
    task: Task,
    programmers: List<Programmer>,
    onClick: (Task) -> Unit
) {
    val assignedProgrammer = programmers.firstOrNull { it.id == task.programmerId }

    Card(
        modifier = Modifier
            .clickable { onClick(task) }
            .padding(vertical = 8.dp)
            .fillMaxWidth(),
        backgroundColor = Theme.colors.surfaceContainerHigh,
        border = BorderStroke(1.dp, Theme.colors.outline),
        elevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = task.name,
                    style = MaterialTheme.typography.h6,
                    color = Theme.materialColors.onSurface
                )

                StatusChip(isCompleted = task.endDate != null)
            }

            Text(
                text = task.description,
                style = MaterialTheme.typography.body2,
                color = Theme.colors.textSecondary
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoItem(
                    icon = Icons.Default.Schedule,
                    label = "Estimación",
                    value = "${task.estimation}h",
                    iconSize = Modifier.size(16.dp)
                )

                InfoItem(
                    icon = Icons.Default.Person,
                    label = "Asignado a",
                    value = assignedProgrammer?.name ?: "Sin asignar",
                    iconSize = Modifier.size(16.dp)
                )

                InfoItem(
                    icon = Icons.Default.Event,
                    label = "Creación",
                    value = task.creationDate.toString(),
                    iconSize = Modifier.size(16.dp)
                )

                if (task.endDate != null) {
                    InfoItem(
                        icon = Icons.Default.DateRange,
                        label = "Finalización",
                        value = task.endDate.toString(),
                        iconSize = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}