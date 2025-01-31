package presentation.theme

import androidx.compose.ui.graphics.Color

/**
 * Paleta de colores principal de la aplicación
 */
object AppColors {
    // Grises - Superficies y Fondos
    val Gray900 = Color(0xFF121212) // Background principal
    val Gray800 = Color(0xFF1E1E1E) // Surface estándar
    val Gray750 = Color(0xFF242424) // Surface elevada
    val Gray700 = Color(0xFF2C2C2C) // Surface alta
    val Gray600 = Color(0xFF363636) // Bordes suaves
    val Gray500 = Color(0xFF555555) // Bordes medios
    val Gray400 = Color(0xFF7B7B7B) // Texto deshabilitado
    val Gray300 = Color(0xFF9E9E9E) // Texto secundario
    val Gray200 = Color(0xFFE0E0E0) // Texto primario

    // Azules - Color Primario
    val Blue400 = Color(0xFF4B9FFF) // Hover/Active #4B9FFF
    val Blue500 = Color(0xFF2979FF) // Default    #2979FF
    val Blue600 = Color(0xFF2264D1) // Pressed    #2264D1

    // Menta - Color Secundario
    val Mint400 = Color(0xFF4DD0B3) // Hover/Active #4DD0B3
    val Mint500 = Color(0xFF26A69A) // Default    #26A69A
    val Mint600 = Color(0xFF00897B) // Pressed    #00897B

    // Estados - Feedback
    val Success = Color(0xFF4CAF50)       // Verde     #4CAF50
    val SuccessContainer = Color(0xFF1B5E20) // Verde oscuro #1B5E20

    val Error = Color(0xFFEF5350)         // Rojo      #EF5350
    val ErrorContainer = Color(0xFF811111) // Rojo oscuro #811111

    val Warning = Color(0xFFFFB74D)       // Naranja   #FFB74D
    val WarningContainer = Color(0xFF663D00) // Naranja oscuro #663D00

    val Info = Color(0xFF64B5F6)         // Azul info  #64B5F6
    val InfoContainer = Color(0xFF0D47A1) // Azul oscuro #0D47A1
}

/**
 * Opacidades estándar para estados de componentes
 */
object AlphaValues {
    const val DisabledAlpha = 0.38f
    const val HoverOverlayAlpha = 0.08f
    const val PressedOverlayAlpha = 0.12f
    const val DraggedOverlayAlpha = 0.16f
    const val FocusOverlayAlpha = 0.12f
}