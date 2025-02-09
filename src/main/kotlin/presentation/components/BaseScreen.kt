package presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import presentation.components.navigation.SidebarMenu
import presentation.theme.Theme

@Composable
fun BaseScreen(
    selectedItem: Int,
    onItemSelected: (Int) -> Unit,
    navigator: Navigator?,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Theme.materialColors.background
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            SidebarMenu(
                selectedItem = selectedItem,
                onItemSelected = onItemSelected,
                navigator = navigator
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(24.dp)
            ) {
                content()
            }
        }
    }
}