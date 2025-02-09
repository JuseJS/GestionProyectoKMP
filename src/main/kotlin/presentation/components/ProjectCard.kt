package presentation.components

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import domain.model.Project
import presentation.theme.Theme

@Composable
fun ProjectCard(
    project: Project,
    onClick: (Project) -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current,
                onClick = { onClick(project) }
            )
            .scale(if (isHovered) 1.01f else 1f),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = if (isHovered)
            Theme.colors.surfaceContainerHigh
        else
            Theme.colors.surfaceContainer,
        elevation = if (isHovered) 8.dp else 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Nombre del proyecto
            Text(
                text = project.name,
                style = MaterialTheme.typography.h6,
                color = Theme.materialColors.onBackground
            )

            // Descripción
            Text(
                text = project.description,
                style = MaterialTheme.typography.body2,
                color = Theme.colors.textSecondary
            )

            Divider(color = Theme.colors.outline)

            // Empresa cliente
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Business,
                    contentDescription = "Empresa",
                    tint = Theme.materialColors.primary
                )
                Text(
                    text = project.clientName,
                    style = MaterialTheme.typography.body2,
                    color = Theme.materialColors.onBackground
                )
            }

            // Fechas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Fecha de inicio
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = "Fecha inicio",
                        tint = Theme.materialColors.primary
                    )
                    Text(
                        text = "Inicio: ${project.startDate}",
                        style = MaterialTheme.typography.body2,
                        color = Theme.materialColors.onBackground
                    )
                }

                // Estado o fecha de finalización
                project.endDate?.let { endDate ->
                    Text(
                        text = "Finalizado: $endDate",
                        style = MaterialTheme.typography.body2,
                        color = Theme.colors.textSecondary
                    )
                } ?: Card(
                    backgroundColor = Theme.materialColors.primary,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Activo",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        color = Theme.materialColors.onPrimary,
                        style = MaterialTheme.typography.caption
                    )
                }
            }
        }
    }
}