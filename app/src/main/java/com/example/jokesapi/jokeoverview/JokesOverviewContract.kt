package com.example.jokesapi.jokeoverview

import androidx.annotation.StringRes
import com.example.jokesapi.jokecategory.JokesCategoryContract.State
import com.example.jokesapi.shared.model.JokeType
import com.example.jokesapi.shared.model.JokeUi

class JokesOverviewContract {

    sealed class State {
        data object Idle : State()
        data object Loading : State()
        data class DisplayJokeCategories(val jokes: List<JokeType>, val randomJoke: JokeUi) : State()
        data class Error(@StringRes val messageRes: Int) : State()
    }

    sealed class SideEffect {
        data class NavigateToJokeCategory(val jokeType: JokeType) : SideEffect()
    }

    sealed class Event {
        data class OnJokeTypeClicked(val jokeType: JokeType) : Event()
        data object OnRetryClicked : Event()
    }
}