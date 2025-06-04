package com.example.jokesapi.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun JokesApiTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colorScheme: ThemeColors
    val typography: JokeTypography
    if (!isDarkTheme) {
        colorScheme = LightThemeColors
        typography = LightTypography
    } else {
        colorScheme = DarkThemeColors
        typography = DarkTypography
    }

    CompositionLocalProvider(
        LocalThemeColors provides colorScheme,
        LocalThemeTypography provides typography,
        content = content
    )
}

object JokesApiTheme {
    val colors: ThemeColors
        @Composable
        get() = LocalThemeColors.current

    val typography: JokeTypography
        @Composable
        get() = LocalThemeTypography.current
}