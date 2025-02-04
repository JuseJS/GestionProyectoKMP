package presentation.components.project

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import presentation.theme.Theme

@Composable
fun FilterChip(
    selected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .height(40.dp)
            .padding(horizontal = 4.dp),
        shape = MaterialTheme.shapes.medium,
        backgroundColor = Theme.colors.surfaceContainer,
        elevation = 0.dp
    ) {
        TextButton(
            onClick = onClick,
            contentPadding = PaddingValues(horizontal = 16.dp),
            colors = ButtonDefaults.textButtonColors(
                contentColor = if (selected)
                    Theme.materialColors.primary
                else
                    Theme.colors.textSecondary
            )
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = "Filtrar",
                    tint = if (selected)
                        Theme.materialColors.primary
                    else
                        Theme.colors.textSecondary
                )
                Text(
                    text = if (selected)
                        "Mis Proyectos"
                    else
                        "Todos los Proyectos"
                )
            }
        }
    }
}