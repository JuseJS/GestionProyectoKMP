import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import cafe.adriel.voyager.navigator.Navigator
import di.apiModule
import di.repositoryModule
import di.useCaseModule
import di.viewModelModule
import org.koin.core.context.startKoin
import presentation.screen.LoginScreen
import presentation.theme.AppTheme

@Composable
@Preview
fun App() {
    AppTheme {
        Navigator(LoginScreen())
    }
}

fun main() {
    startKoin {
        modules(
            apiModule,
            repositoryModule,
            useCaseModule,
            viewModelModule
        )
    }

    application {
        val windowState = rememberWindowState(
            size = DpSize.Unspecified,
            placement = WindowPlacement.Maximized
        )

        Window(
            onCloseRequest = ::exitApplication,
            title = "NeoTech",
            state = windowState
        ) {
            App()
        }
    }
}