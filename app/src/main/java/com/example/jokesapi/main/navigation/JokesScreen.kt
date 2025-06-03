package com.example.jokesapi.main.navigation

import com.example.jokesapi.shared.model.JokeType
import kotlinx.serialization.Serializable

sealed class JokesScreen {

    @Serializable
    object JokeOverview : JokesScreen()

    @Serializable
    data class JokeCategory(val jokeType: JokeType) : JokesScreen()
}