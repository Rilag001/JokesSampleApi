package com.example.jokesapi.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val LightType = Color(0xFF000000)
val LightContent = Color(0xFFFFFFFF)

val LightBackground = Color(0xFFDAD9D7)

val DarkType = Color(0xFFFFFFFF)
val DarkContent = Color(0xFF000000)
val DarkBackground = Color(0xFF343131)

val ForestGreen = Color(0xFF67B717)

data class ThemeColors(
    val type: Color = Color.Unspecified,
    val content: Color = Color.Unspecified,
    val background: Color = Color.Unspecified,
    val forestGreen: Color = ForestGreen,
)

val LightThemeColors = ThemeColors(
    type = LightType,
    content = LightContent,
    background = LightBackground,
)

val DarkThemeColors = ThemeColors(
    type = DarkType,
    content = DarkContent,
    background = DarkBackground,
)

val LocalThemeColors = staticCompositionLocalOf { ThemeColors() }