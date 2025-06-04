package com.example.jokesapi.shared.model

data class JokeUi(
    val id: Int,
    val jokeType: JokeType,
    val setup: String,
    val punchline: String,
)