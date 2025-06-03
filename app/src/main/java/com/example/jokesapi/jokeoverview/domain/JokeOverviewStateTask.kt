package com.example.jokesapi.jokeoverview.domain

import com.example.jokesapi.R
import com.example.jokesapi.data.JokesRepository
import com.example.jokesapi.data.remote.model.JokesResultFailure
import com.example.jokesapi.data.toUiModel
import com.example.jokesapi.jokeoverview.JokeOverviewListItem
import com.example.jokesapi.jokeoverview.JokesOverviewContract
import com.example.jokesapi.shared.model.JokeType
import javax.inject.Inject

class JokeOverviewStateTask @Inject constructor(
    private val repository: JokesRepository,
) {
    suspend operator fun invoke(): JokesOverviewContract.State {
        val result = repository.getAllJokes()
        return if (result.isSuccessful && !result.data.isNullOrEmpty()) {
            val list = mutableListOf<JokeOverviewListItem>()
            list.add(JokeOverviewListItem.Title(R.string.joke_categories))
            list.addAll(JokeType.entries.map { JokeOverviewListItem.JokeTypeCard(jokeType = it) })
            list.add(JokeOverviewListItem.Title(R.string.random_joke))
            list.add(JokeOverviewListItem.RandomJokeCard(randomJoke = result.data.random().toUiModel()))

            JokesOverviewContract.State.DisplayJokeCategories(list = list)
        } else {
            val message = when (result.failure) {
                JokesResultFailure.NetworkOffline -> R.string.error_network
                JokesResultFailure.NotFound -> R.string.error_not_found
                else -> R.string.error_unexpected
            }
            JokesOverviewContract.State.Error(messageRes = message)
        }
    }
}