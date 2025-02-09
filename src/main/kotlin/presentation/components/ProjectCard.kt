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
import androidx.compose.material.icons.filled.Person
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
    isDetailView: Boolean = false,
    onClick: ((Project) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isClickable = onClick != null

    Card(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (isClickable) {
                    Modifier.clickable(
                        interactionSource = interactionSource,
                        indication = LocalIndication.current,
                        onClick = { onClick?.invoke(project) }
                    ).scale(if (isHovered) 1.01f else 1f)
                } else Modifier
            ),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = if (isHovered)
            Theme.colors.surfaceContainerHigh
        else
            Theme.colors.surfaceContainer,
        elevation = if (isHovered && isClickable) 8.dp else 4.dp
    ) {
        Column(
            modifier = Modifier.padding(if (isDetailView) 24.dp else 16.dp),
            verticalArrangement = Arrangement.spacedBy(if (isDetailView) 16.dp else 12.dp)
        ) {
            // Título
            Text(
                text = project.name,
                style = if (isDetailView)
                    MaterialTheme.typography.h4
                else
                    MaterialTheme.typography.h6,
                color = Theme.materialColors.onBackground
            )

            // Descripción
            Text(
                text = project.description,
                style = if (isDetailView)
                    MaterialTheme.typography.body1
                else
                    MaterialTheme.typography.body2,
                color = Theme.colors.textSecondary
            )

            Divider(color = Theme.colors.outline)

            // Información adicional
            if (isDetailView) {
                // Vista detalle con tres InfoItems
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    InfoItem(
                        icon = Icons.Default.Business,
                        label = "Cliente",
                        value = project.clientId.toString()
                    )

                    InfoItem(
                        icon = Icons.Default.Schedule,
                        label = "Fecha de inicio",
                        value = project.startDate.toString()
                    )

                    InfoItem(
                        icon = Icons.Default.Person,
                        label = "Estado",
                        value = if (project.endDate != null) "Finalizado" else "En progreso"
                    )
                }
            } else {
                // Vista de lista con información compacta
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Cliente y fecha de inicio
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Business,
                                contentDescription = "Empresa",
                                tint = Theme.materialColors.primary
                            )
                            Text(
                                text = project.clientId.toString(),
                                style = MaterialTheme.typography.body2,
                                color = Theme.materialColors.onBackground
                            )
                        }

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
                    }

                    // Estado
                    if (project.endDate != null) {
                        Text(
                            text = "Finalizado: ${project.endDate}",
                            style = MaterialTheme.typography.body2,
                            color = Theme.colors.textSecondary
                        )
                    } else {
                        Card(
                            backgroundColor = Theme.materialColors.primary,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "Activo",
                                modifier = Modifier.padding(
                                    horizontal = 12.dp,
                                    vertical = 4.dp
                                ),
                                color = Theme.materialColors.onPrimary,
                                style = MaterialTheme.typography.caption
                            )
                        }
                    }
                }
            }
        }
    }
}