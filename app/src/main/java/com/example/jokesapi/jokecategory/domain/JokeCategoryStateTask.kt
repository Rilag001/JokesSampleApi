package com.example.jokesapi.jokecategory.domain

import com.example.jokesapi.R
import com.example.jokesapi.data.JokesRepository
import com.example.jokesapi.data.remote.model.JokesResultFailure
import com.example.jokesapi.data.toUiModel
import com.example.jokesapi.jokecategory.JokesCategoryContract
import com.example.jokesapi.shared.model.JokeType
import java.util.Locale
import javax.inject.Inject

class JokeCategoryStateTask @Inject constructor(
    private val repository: JokesRepository,
) {
    suspend operator fun invoke(jokeType: JokeType): JokesCategoryContract.State {
        val result = repository.getJokesByType(type = jokeType)
        return if (result.isSuccessful && !result.data.isNullOrEmpty()) {
            val jokes = result.data.map { it.toUiModel() }
            val title = jokes.first().jokeType.label.replaceFirstChar { it.titlecase(Locale.getDefault()) }
            JokesCategoryContract.State.DisplayJokes(
                title = title,
                jokes = jokes,
            )
        } else {
            val message = when (result.failure) {
                JokesResultFailure.NetworkOffline -> R.string.error_network
                JokesResultFailure.NotFound -> R.string.error_not_found
                else -> R.string.error_unexpected
            }
            JokesCategoryContract.State.Error(message)
        }
    }
}