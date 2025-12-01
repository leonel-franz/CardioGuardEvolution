package com.cardioguard.evolution.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// ---- Light/Dark palettes ----
private val LightColors: ColorScheme = lightColorScheme(
    primary = AzulPrimario,
    onPrimary = Color.White,
    secondary = NaranjaModerado,
    onSecondary = Color.White,
    error = RojoCritico,
    onError = Color.White,
    background = Gray100,
    onBackground = AzulPrimario,
    surface = Color.White,
    onSurface = AzulPrimario,
)

private val DarkColors: ColorScheme = darkColorScheme(
    primary = AzulPrimario,
    onPrimary = Color.White,
    secondary = NaranjaModerado,
    onSecondary = Color.Black,
    error = RojoCritico,
    onError = Color.Black,
    background = AzulOscuro,
    onBackground = Color.White,
    surface = AzulOscuro,
    onSurface = Color.White,
)

@Composable
fun CardioGuardTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (useDarkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = AppTypography,
        content = content
    )
}
