package com.example.jokesapi.jokeoverview.domain

import com.example.jokesapi.R
import com.example.jokesapi.data.JokesRepository
import com.example.jokesapi.data.remote.model.JokesResultFailure
import com.example.jokesapi.data.toUiModel
import com.example.jokesapi.jokeoverview.JokesOverviewContract
import com.example.jokesapi.shared.model.JokeType
import javax.inject.Inject

class JokeOverviewStateTask @Inject constructor(
    private val repository: JokesRepository,
) {
    suspend operator fun invoke(): JokesOverviewContract.State {
        val result = repository.getAllJokes()
        return if (result.isSuccessful && !result.data.isNullOrEmpty()) {
            JokesOverviewContract.State.DisplayJokeCategories(
                jokes = JokeType.entries,
                randomJoke = result.data.random().toUiModel(),
            )
        } else {
            val message = when (result.failure) {
                JokesResultFailure.NetworkOffline -> R.string.error_network
                JokesResultFailure.NotFound -> R.string.error_not_found
                else -> R.string.error_unexpected
            }
            JokesOverviewContract.State.Error(message)
        }
    }
}