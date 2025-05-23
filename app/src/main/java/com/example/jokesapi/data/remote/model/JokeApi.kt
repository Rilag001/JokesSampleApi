package com.example.jokesapi.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JokeApi(
    @SerialName("id") val id: Int,
    @SerialName("type") val type: String,
    @SerialName("setup") val setup: String,
    @SerialName("punchline") val punchline: String,
)