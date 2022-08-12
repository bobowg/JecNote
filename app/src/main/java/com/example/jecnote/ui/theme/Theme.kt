package com.example.jecnote.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

private val DarkColorPalette = darkColors(
    primary = rwGreen,
    primaryVariant = rwGreenDark,
    secondary = rwRed,
)

private val LightColorPalette = lightColors(
   primary = rwGreen,
    primaryVariant = rwGreenDark,
    secondary = rwRed
)

@Composable
fun JecNoteTheme(content: @Composable () -> Unit) {
    val darkTheme = isSystemInDarkTheme() || JetNotesThemeSettings.isDarkThemeEnable
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

object JetNotesThemeSettings{
    var isDarkThemeEnable by mutableStateOf(false)
}