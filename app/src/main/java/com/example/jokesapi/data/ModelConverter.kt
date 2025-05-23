package com.example.jokesapi.data

import com.example.jokesapi.data.local.database.JokesEntity
import com.example.jokesapi.data.remote.model.JokeApi
import com.example.jokesapi.shared.model.JokeType
import com.example.jokesapi.shared.model.JokeUi

fun JokesEntity.toJokeApi(): JokeApi {
    return JokeApi(
        id = this.id,
        type = this.type,
        setup = this.setup,
        punchline = this.punchline
    )
}

fun JokeApi.toJokeEntity(): JokesEntity {
    return JokesEntity(
        id = this.id,
        type = this.type,
        setup = this.setup,
        punchline = this.punchline
    )
}

fun JokeApi.toUiModel(): JokeUi {
    return JokeUi(
        jokeType = this.type.toJokeType(),
        setup = this.setup,
        punchline = this.punchline,
    )
}

fun String.toJokeType(): JokeType {
    return when(this) {
        JokeType.PROGRAMMING.label -> JokeType.PROGRAMMING
        JokeType.KNOCK_KNOCK.label -> JokeType.KNOCK_KNOCK
        else -> JokeType.GENERAL
    }
}