package presentation.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

data class AppColorScheme(
    // Superficies adicionales
    val surfaceContainer: Color = AppColors.Gray750,
    val surfaceContainerHigh: Color = AppColors.Gray700,

    // Estados adicionales para interacciones
    val primaryHover: Color = AppColors.Blue400,
    val primaryPressed: Color = AppColors.Blue600,
    val secondaryHover: Color = AppColors.Mint400,
    val secondaryPressed: Color = AppColors.Mint600,

    // Sistema de bordes personalizado
    val outline: Color = AppColors.Gray500,
    val outlineVariant: Color = AppColors.Gray600,

    // Texto secundario y estados
    val textSecondary: Color = AppColors.Gray300,
    val textDisabled: Color = AppColors.Gray400,

    // Contenedores de estados
    val successContainer: Color = AppColors.SuccessContainer,
    val errorContainer: Color = AppColors.ErrorContainer,
    val warningContainer: Color = AppColors.WarningContainer,
    val infoContainer: Color = AppColors.InfoContainer,

    // Estados adicionales
    val success: Color = AppColors.Success,
    val warning: Color = AppColors.Warning,
    val info: Color = AppColors.Info
)

/**
 * CompositionLocal para nuestros colores adicionales
 */
val LocalAppColors = staticCompositionLocalOf { AppColorScheme() }

/**
 * Proveedor principal del tema de la aplicación
 */
@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    // Configuración de colores para MaterialTheme
    val materialColors = darkColors(
        primary = AppColors.Blue500,
        primaryVariant = AppColors.Blue600,
        secondary = AppColors.Mint500,
        secondaryVariant = AppColors.Mint600,
        background = AppColors.Gray900,
        surface = AppColors.Gray800,
        error = AppColors.Error,
        onPrimary = Color.White,
        onSecondary = Color.White,
        onBackground = AppColors.Gray200,
        onSurface = AppColors.Gray200,
        onError = Color.White
    )

    // Nuestros colores adicionales
    val appColorScheme = AppColorScheme()

    CompositionLocalProvider(LocalAppColors provides appColorScheme) {
        MaterialTheme(
            colors = materialColors,
            content = content
        )
    }
}

/**
 * Objeto para acceder a todos los colores del tema
 */
object Theme {
    /**
     * Colores base de Material Design
     */
    val materialColors: Colors
        @Composable
        get() = MaterialTheme.colors

    /**
     * Colores adicionales personalizados
     */
    val colors: AppColorScheme
        @Composable
        get() = LocalAppColors.current

    /**
     * Función de utilidad para obtener el color de texto apropiado según el fondo
     */
    @Composable
    fun getTextColorFor(backgroundColor: Color): Color {
        return when (backgroundColor) {
            materialColors.primary, materialColors.secondary -> materialColors.onPrimary
            colors.success, colors.warning, colors.info -> materialColors.onPrimary
            else -> materialColors.onSurface
        }
    }
}

/**
 * Extensiones útiles para colores
 */
fun Color.withAlpha(alpha: Float): Color = this.copy(alpha = alpha)