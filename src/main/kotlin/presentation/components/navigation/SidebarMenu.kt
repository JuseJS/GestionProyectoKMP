package presentation.components.navigation

import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.screen.LoginScreen
import presentation.screen.ProjectsScreen
import presentation.screen.WelcomeScreen
import presentation.theme.Theme
import presentation.viewmodel.LoginViewModel

@Composable
fun SidebarMenu(
    selectedItem: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    navigator: Navigator?
) {
    val sidebarViewModel = remember { SidebarMenuComponent() }

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
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "NeoTech",
                    style = MaterialTheme.typography.h6,
                    color = Theme.materialColors.onBackground,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

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

            Button(
                onClick = {
                    sidebarViewModel.loginViewModel.logout()
                    navigator?.push(LoginScreen())
                },
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

private class SidebarMenuComponent : KoinComponent {
    val loginViewModel: LoginViewModel by inject()
}