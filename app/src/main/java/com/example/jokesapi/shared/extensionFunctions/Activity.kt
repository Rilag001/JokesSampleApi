package com.example.jokesapi.shared.extensionFunctions

import android.graphics.Color
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge

fun ComponentActivity.setTransparentEdgeToEdge() {
    val style = SystemBarStyle.light(scrim = Color.TRANSPARENT, darkScrim = Color.TRANSPARENT)
    this.enableEdgeToEdge(statusBarStyle = style, navigationBarStyle = style)
}