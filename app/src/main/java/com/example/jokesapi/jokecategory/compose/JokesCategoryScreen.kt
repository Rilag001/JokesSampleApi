package com.example.jokesapi.jokecategory.compose

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.jokesapi.R
import com.example.jokesapi.jokecategory.JokesCategoryContract
import com.example.jokesapi.jokecategory.JokesCategoryViewModel
import com.example.jokesapi.shared.compose.JokeCard
import com.example.jokesapi.shared.compose.JokeErrorMessage
import com.example.jokesapi.shared.model.JokeType
import com.example.jokesapi.shared.model.JokeUi
import com.example.jokesapi.ui.theme.JokesApiTheme

@Composable
fun JokesCategoryScreen(
    navController: NavController,
    jokeType: JokeType,
    viewModel: JokesCategoryViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::send

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is JokesCategoryContract.SideEffect.NavigateBack -> {
                    navController.popBackStack()
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        viewModel.send(JokesCategoryContract.Event.Init(jokeType = jokeType))
    }

    BackHandler {
        onEvent(JokesCategoryContract.Event.OnBackClicked)
    }

    JokesCategoryScreenContent(
        modifier = Modifier,
        state = state,
        onEvent = onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun JokesCategoryScreenContent(
    modifier: Modifier,
    state: JokesCategoryContract.State,
    onEvent: (JokesCategoryContract.Event) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                modifier = Modifier,
                title = {
                    when (state) {
                        is JokesCategoryContract.State.DisplayJokes -> {
                            Text(text = state.title, style = JokesApiTheme.typography.title)
                        }
                        else -> Unit
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { onEvent(JokesCategoryContract.Event.OnBackClicked) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "",
                            tint = JokesApiTheme.colors.type,
                            modifier = Modifier.size(24.dp),
                        )
                    }
                },
                colors = TopAppBarDefaults
                    .topAppBarColors(containerColor = JokesApiTheme.colors.content),
                windowInsets = TopAppBarDefaults.windowInsets
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .background(color = JokesApiTheme.colors.background)
                    .padding(paddingValues)
                    .windowInsetsPadding(WindowInsets.displayCutout.only(WindowInsetsSides.Horizontal))
                    .fillMaxSize()
            ) {
                when (state) {
                    JokesCategoryContract.State.Idle -> Unit
                    is JokesCategoryContract.State.Error -> {
                        JokeErrorMessage(
                            modifier = Modifier.align(Alignment.Center),
                            messageRes = state.messageRes
                        )
                    }
                    is JokesCategoryContract.State.DisplayJokes -> {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            contentPadding = PaddingValues(16.dp),
                        ) {
                            items(
                                items = state.jokes,
                                key = { joke: JokeUi -> joke.id },
                            ) { joke: JokeUi ->
                                JokeCard(joke = joke)
                            }
                        }
                    }
                }
            }
        }
    )
}

@Preview
@Composable
private fun PreviewJokeOverViewScreenDisplayJokes() {
    PreviewJokeOverViewScreenDisplayJokesContent()
}

@Preview
@Composable
private fun PreviewJokeOverViewScreenDisplayJokesDark() {
    JokesApiTheme(isDarkTheme = true) {
        PreviewJokeOverViewScreenDisplayJokesContent()
    }
}

@Composable
private fun PreviewJokeOverViewScreenDisplayJokesContent() {
    val joke1 = JokeUi(
        id = 1,
        jokeType = JokeType.GENERAL,
        setup = "What kind of shoes does a thief wear?",
        punchline = "Sneakers"
    )
    val joke2 = joke1.copy(id = 2)
    val joke3 = joke1.copy(id = 3)
    JokesCategoryScreenContent(
        modifier = Modifier,
        state = JokesCategoryContract.State.DisplayJokes(
            title = "General",
            jokes = listOf(joke1, joke2, joke3),
        ),
        onEvent = {}
    )
}

@Preview
@Composable
private fun PreviewJokeOverViewScreenError() {
    PreviewJokeOverViewScreenErrorContent()
}

@Preview
@Composable
private fun PreviewJokeOverViewScreenErrorDark() {
    JokesApiTheme(isDarkTheme = true) {
        PreviewJokeOverViewScreenErrorContent()
    }
}

@Composable
private fun PreviewJokeOverViewScreenErrorContent() {
    JokesCategoryScreenContent(
        modifier = Modifier,
        state = JokesCategoryContract.State.Error(messageRes = R.string.error_not_found),
        onEvent = {}
    )
}