package com.example.jokesapi.jokeoverview

import androidx.annotation.StringRes
import com.example.jokesapi.shared.model.JokeType
import com.example.jokesapi.shared.model.JokeUi

class JokesOverviewContract {

    sealed class State {
        data object Idle : State()
        data object Loading : State()
        data class DisplayJokeCategories(val list: List<JokeOverviewListItem>) : State()
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

sealed class JokeOverviewListItem() {
    val id: Int = IDGenerator.nextId()

    data class Title(
        @StringRes val titleRes: Int
    ) : JokeOverviewListItem()

    data class JokeTypeCard(
        val jokeType: JokeType,
    ) : JokeOverviewListItem()

    data class RandomJokeCard(
        val randomJoke: JokeUi,
    ) : JokeOverviewListItem()

    private object IDGenerator {
        private var id = 0
        fun nextId() = id++
    }
}