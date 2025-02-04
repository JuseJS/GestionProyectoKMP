package presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import presentation.theme.Theme

@Composable
fun InfoItem(
    icon: ImageVector,
    label: String,
    value: String,
    iconSize: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Theme.materialColors.primary,
            modifier = iconSize
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