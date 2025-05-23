package com.example.jokesapi.jokecategory.compose

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jokesapi.R
import com.example.jokesapi.jokecategory.JokesCategoryContract
import com.example.jokesapi.shared.compose.JokeErrorMessage
import com.example.jokesapi.shared.compose.JokeCard
import com.example.jokesapi.shared.model.JokeType
import com.example.jokesapi.shared.model.JokeUi
import com.example.jokesapi.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JokesCategoryScreen(
    modifier: Modifier,
    state: JokesCategoryContract.State,
    onEvent: (JokesCategoryContract.Event) -> Unit,
) {
    BackHandler {
        onEvent(JokesCategoryContract.Event.OnBackClicked)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                modifier = Modifier.background(Color.White),
                title = {
                    when (state) {
                        is JokesCategoryContract.State.DisplayJokes -> {
                            Text(text = state.title, color = Color.Black)
                        }
                        else -> Unit
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { onEvent(JokesCategoryContract.Event.OnBackClicked) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "",
                            tint = Color.Black,
                            modifier = Modifier.size(24.dp),
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                windowInsets = TopAppBarDefaults.windowInsets
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .background(color = Color(0xFFF2EDE8))
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
                        LazyColumn (
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            contentPadding = PaddingValues(16.dp),
                        ) {
                            items(count = state.jokes.size) {
                                JokeCard(joke = state.jokes[it])
                            }
                            item {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp)
                                        .align(Alignment.Center),
                                    text = stringResource(R.string.no_more_jokes),
                                    style = Typography.bodyLarge, color = Color.Black,
                                    textAlign = TextAlign.Center
                                )
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
private fun PreviewJokeOverViewScreen() {
    val joke = JokeUi(
        jokeType = JokeType.GENERAL,
        setup = "What kind of shoes does a thief wear?",
        punchline = "Sneakers"
    )
    JokesCategoryScreen(
        modifier = Modifier,
        state = JokesCategoryContract.State.DisplayJokes(
            title = "General",
            jokes = listOf(joke, joke, joke),
        ),
        onEvent = {}
    )
}