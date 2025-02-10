package presentation.components.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import presentation.theme.Theme

@Composable
fun TaskDetailsCard(
    task: Task,
    programmer: Programmer?,
    onAssignClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Theme.colors.surfaceContainer,
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = task.name,
                    style = MaterialTheme.typography.h4,
                    color = Theme.materialColors.onBackground
                )

                Card(
                    backgroundColor = if (task.endDate != null)
                        Theme.materialColors.primary
                    else
                        Theme.colors.surfaceContainerHigh,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = if (task.endDate != null) "Completada" else "En progreso",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        color = if (task.endDate != null)
                            Theme.materialColors.onPrimary
                        else
                            Theme.colors.textSecondary,
                        style = MaterialTheme.typography.caption
                    )
                }
            }

            Text(
                text = task.description,
                style = MaterialTheme.typography.body1,
                color = Theme.colors.textSecondary
            )

            Divider(color = Theme.colors.outline)

            // Información detallada
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TaskDetailItem(
                    icon = Icons.Default.Schedule,
                    label = "Tiempo Estimado",
                    value = "${task.endDate} horas"
                )

                TaskDetailItem(
                    icon = Icons.Default.DateRange,
                    label = "Fecha de Creación",
                    value = task.creationDate.toString()
                )

                if (task.endDate != null) {
                    TaskDetailItem(
                        icon = Icons.Default.Event,
                        label = "Fecha de Finalización",
                        value = task.endDate.toString()
                    )
                }

                TaskDetailItem(
                    icon = Icons.Default.Person,
                    label = "Programador Asignado",
                    value = programmer?.name ?: "Sin asignar"
                )

                if (task.programmerId == null) {
                    Button(
                        onClick = onAssignClick,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Theme.materialColors.primary,
                            contentColor = Theme.materialColors.onPrimary
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Asignar Programador")
                    }
                }
            }
        }
    }
}