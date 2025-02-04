package presentation.components.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import presentation.theme.Theme

@Composable
fun TaskDetailItem(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Theme.materialColors.primary
        )
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.caption,
                color = Theme.colors.textSecondary
            )
            Text(
                text = value,
                style = MaterialTheme.typography.body2,
                color = Theme.materialColors.onBackground
            )
        }
    }
}