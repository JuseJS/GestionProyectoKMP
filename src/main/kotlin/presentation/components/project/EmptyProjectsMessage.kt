package presentation.components.project

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import presentation.theme.Theme

@Composable
fun EmptyProjectsMessage() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No hay proyectos para mostrar",
            style = MaterialTheme.typography.h6,
            color = Theme.colors.textSecondary
        )
    }
}