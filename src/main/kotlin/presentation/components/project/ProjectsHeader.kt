package presentation.components.project

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import presentation.theme.Theme

@Composable
fun ProjectsHeader(
    showOnlyUserProjects: Boolean,
    onFilterChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Proyectos Activos",
            style = MaterialTheme.typography.h4,
            color = Theme.materialColors.onBackground
        )

        FilterChip(
            selected = showOnlyUserProjects,
            onClick = { onFilterChange(!showOnlyUserProjects) }
        )
    }
}