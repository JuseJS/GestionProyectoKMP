package presentation.components.project

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.model.Programmer
import presentation.theme.Theme
import kotlin.collections.forEach

@Composable
fun AssignProgrammerDialog(
    availableProgrammers: List<Programmer>,
    onProgrammerSelected: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "Asignar Programador",
                style = MaterialTheme.typography.h6,
                color = Theme.materialColors.onBackground
            )
        },
        text = {
            if (availableProgrammers.isEmpty()) {
                Text(
                    "No hay programadores disponibles para asignar",
                    color = Theme.colors.textSecondary
                )
            } else {
                Column {
                    availableProgrammers.forEach { programmer ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onProgrammerSelected(programmer.id)
                                    onDismiss()
                                }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = programmer.name,
                                color = Theme.materialColors.onBackground,
                                style = MaterialTheme.typography.body1
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
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