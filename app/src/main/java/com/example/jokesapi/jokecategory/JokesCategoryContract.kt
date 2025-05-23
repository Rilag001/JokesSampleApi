package com.example.jokesapi.jokecategory

import androidx.annotation.StringRes
import com.example.jokesapi.shared.model.JokeType
import com.example.jokesapi.shared.model.JokeUi

class JokesCategoryContract {

    sealed class State {
        data object Idle : State()
        data class DisplayJokes(val title: String, val jokes: List<JokeUi>) : State()
        data class Error(@StringRes val messageRes: Int) : State()
    }

    sealed class Event {
        data class Init(val jokeType: JokeType) : Event()
        data object OnBackClicked : Event()
    }

    sealed class SideEffect {
        data object NavigateBack : SideEffect()
    }
}