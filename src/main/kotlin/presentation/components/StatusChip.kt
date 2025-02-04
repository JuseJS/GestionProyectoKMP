package presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import presentation.theme.Theme

@Composable
fun StatusChip(
    isCompleted: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        backgroundColor = if (isCompleted)
            Theme.materialColors.primary
        else
            Theme.colors.surfaceContainerHigh,
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
    ) {
        Text(
            text = if (isCompleted) "Completada" else "En progreso",
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            color = if (isCompleted)
                Theme.materialColors.onPrimary
            else
                Theme.colors.textSecondary,
            style = MaterialTheme.typography.caption
        )
    }
}