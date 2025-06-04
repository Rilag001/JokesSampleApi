package com.example.jokesapi.jokecategory

import app.cash.turbine.test
import com.example.jokesapi.R
import com.example.jokesapi.jokecategory.JokesCategoryContract.SideEffect
import com.example.jokesapi.jokecategory.JokesCategoryContract.State
import com.example.jokesapi.jokecategory.domain.JokeCategoryStateTask
import com.example.jokesapi.shared.model.JokeType
import com.example.jokesapi.shared.model.JokeUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class JokesCategoryViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val stateTask: JokeCategoryStateTask = mock()

    private val viewModel: JokesCategoryViewModel = JokesCategoryViewModel(
        ioDispatcher = testDispatcher,
        stateTask = stateTask,
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `event init, state DisplayJokes`() = runTest {
        viewModel.state.test {
            val jokeType = JokeType.GENERAL
            val jokeUi = JokeUi(
                id = 1,
                jokeType = jokeType,
                setup = "setup",
                punchline = "punchline"
            )
            val expectedState1 = State.Idle
            val expectedState2 = State.DisplayJokes(title = "title", jokes = listOf(jokeUi))
            whenever(stateTask.invoke(jokeType)).thenReturn(expectedState2)

            viewModel.send(JokesCategoryContract.Event.Init(jokeType))
            val state1 = awaitItem()
            assert(state1 == expectedState1)
            val state2 = awaitItem()
            assert(state2 == expectedState2)
        }
    }

    @Test
    fun `event init, state Network error`() = runTest {
        viewModel.state.test {
            val jokeType = JokeType.GENERAL
            val expectedState1 = State.Idle
            val expectedState2 = State.Error(R.string.error_network)
            whenever(stateTask.invoke(jokeType)).thenReturn(expectedState2)

            viewModel.send(JokesCategoryContract.Event.Init(jokeType))
            val state1 = awaitItem()
            assert(state1 == expectedState1)
            val state2 = awaitItem()
            assert(state2 == expectedState2)
        }
    }

    @Test
    fun `event OnBackClicked`() = runTest {
        viewModel.sideEffect.test {
            val expectedSideEffect = SideEffect.NavigateBack
            viewModel.send(JokesCategoryContract.Event.OnBackClicked)
            val sideEffect = awaitItem()
            assert(sideEffect == expectedSideEffect)
        }
    }
}