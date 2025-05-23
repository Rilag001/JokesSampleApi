package com.example.jokesapi.jokeoverview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jokesapi.jokeoverview.JokesOverviewContract.SideEffect.NavigateToJokeCategory
import com.example.jokesapi.jokeoverview.domain.JokeOverviewStateTask
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JokesOverviewViewModel @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val stateTask: JokeOverviewStateTask,
): ViewModel() {

    private val mutState = MutableStateFlow<JokesOverviewContract.State>(JokesOverviewContract.State.Idle)
    val state: StateFlow<JokesOverviewContract.State> = mutState.asStateFlow()

    private val mutSideEffect = MutableSharedFlow<JokesOverviewContract.SideEffect>()
    val sideEffect: SharedFlow<JokesOverviewContract.SideEffect> = mutSideEffect.asSharedFlow()

    init {
        viewModelScope.launch(ioDispatcher) {
            loadJokes()
        }
    }

    fun send(event: JokesOverviewContract.Event) {
        viewModelScope.launch(ioDispatcher) {
            reduce(event)
        }
    }

    private suspend fun reduce(event: JokesOverviewContract.Event) {
        when (event) {
            is JokesOverviewContract.Event.OnJokeTypeClicked -> {
                mutSideEffect.emit(NavigateToJokeCategory(jokeType = event.jokeType))
            }
            JokesOverviewContract.Event.OnRetryClicked -> {
                loadJokes()
            }
        }
    }

    private suspend fun loadJokes() {
        mutState.emit(JokesOverviewContract.State.Loading)
        mutState.emit(stateTask())
    }
}