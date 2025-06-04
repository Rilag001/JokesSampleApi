package com.example.jokesapi.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.jokesapi.main.navigation.JokeNavigationStack
import com.example.jokesapi.shared.extensionFunctions.setTransparentEdgeToEdge
import com.example.jokesapi.ui.theme.JokesApiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTransparentEdgeToEdge()
        setContent {
            JokesApiTheme {
                JokeNavigationStack()
            }
        }
    }
}