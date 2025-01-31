package presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import presentation.screen.ProjectsScreen
import presentation.screen.WelcomeScreen
import presentation.theme.Theme

@Composable
fun SidebarMenu(
    selectedItem: Int,
    onItemSelected: (Int) -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier,
    navigator: Navigator?
) {
    Box(
        modifier = modifier
            .width(280.dp)
            .fillMaxHeight()
            .background(Theme.colors.surfaceContainer)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Menú principal
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Logo o título
                Text(
                    text = "NeoTech",
                    style = MaterialTheme.typography.h6,
                    color = Theme.materialColors.onBackground,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                // Elementos del menú
                MenuButton(
                    icon = Icons.Default.Home,
                    text = "Inicio",
                    isSelected = selectedItem == 0,
                    onClick = {
                        onItemSelected(0)
                        navigator?.push(WelcomeScreen())
                    }
                )

                MenuButton(
                    icon = Icons.Default.Folder,
                    text = "Proyectos",
                    isSelected = selectedItem == 1,
                    onClick = {
                        onItemSelected(1)
                        navigator?.push(ProjectsScreen())
                    }
                )
            }

            // Botón de cerrar sesión
            Button(
                onClick = onLogout,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Theme.materialColors.error,
                    contentColor = Theme.materialColors.onError
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.PowerSettingsNew,
                        contentDescription = "Cerrar Sesión"
                    )
                    Text(
                        "Cerrar Sesión",
                        style = MaterialTheme.typography.button
                    )
                }
            }
        }
    }
}

@Composable
private fun MenuButton(
    icon: ImageVector,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) {
        Theme.materialColors.primary
    } else {
        Theme.colors.surfaceContainer
    }

    val contentColor = if (isSelected) {
        Theme.materialColors.onPrimary
    } else {
        Theme.materialColors.onBackground
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = contentColor
        )
        Text(
            text = text,
            style = MaterialTheme.typography.button,
            color = contentColor
        )
    }
}