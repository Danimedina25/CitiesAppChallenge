package com.danifitdev.citiesappchallenge.core.presentation.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val DarkColorScheme = darkColorScheme(
    primary = Green,
    background = Black,
    surface = DarkGray,
    secondary = White,
    tertiary = White,
    primaryContainer = Green30,
    onPrimary = Black,
    onBackground = White,
    onSurface = White,
    onSurfaceVariant = Gray,
    error = DarkRed,
    errorContainer = DarkRed5
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    onPrimary = Color.White,
    onSecondary = Color.Black
)

@Composable
fun CitiesAppChallengeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
        shapes = Shapes,
    )
}