package com.example.jokesapi.jokecategory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jokesapi.jokecategory.JokesCategoryContract.SideEffect
import com.example.jokesapi.jokecategory.JokesCategoryContract.State
import com.example.jokesapi.jokecategory.domain.JokeCategoryStateTask
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
class JokesCategoryViewModel @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val stateTask: JokeCategoryStateTask,
): ViewModel() {

    private val mutState = MutableStateFlow<State>(State.Idle)
    val state: StateFlow<State> = mutState.asStateFlow()

    private val mutSideEffect = MutableSharedFlow<SideEffect>()
    val sideEffect: SharedFlow<SideEffect> = mutSideEffect.asSharedFlow()

    fun send(event: JokesCategoryContract.Event) {
        viewModelScope.launch(ioDispatcher) {
            reduce(event)
        }
    }

    private suspend fun reduce(event: JokesCategoryContract.Event) {
        when (event) {
            is JokesCategoryContract.Event.Init -> {
                mutState.emit(stateTask(jokeType = event.jokeType))
            }
            JokesCategoryContract.Event.OnBackClicked -> {
                mutSideEffect.emit(SideEffect.NavigateBack)
            }
        }
    }
}