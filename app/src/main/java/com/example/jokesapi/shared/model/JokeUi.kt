package com.example.jokesapi.shared.model

data class JokeUi(
    val jokeType: JokeType,
    val setup: String,
    val punchline: String,
)