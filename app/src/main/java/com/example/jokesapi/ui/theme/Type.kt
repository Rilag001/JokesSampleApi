package com.example.jokesapi.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val TextBody = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.5.sp,
    color = LightThemeColors.type,
)
val TextTitle = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 22.sp,
    lineHeight = 28.sp,
    letterSpacing = 0.sp,
    color = LightThemeColors.type,
)

data class JokeTypography(
    val body: TextStyle = TextStyle.Default,
    val title: TextStyle = TextStyle.Default,
)

val LightTypography = JokeTypography(
    body = TextBody,
    title = TextTitle,
)

val DarkTypography = JokeTypography(
    body = TextBody.darkTheme(),
    title = TextTitle.darkTheme(),
)

private fun TextStyle.darkTheme() = this.copy(color = DarkThemeColors.type)

val LocalThemeTypography = staticCompositionLocalOf { JokeTypography() }