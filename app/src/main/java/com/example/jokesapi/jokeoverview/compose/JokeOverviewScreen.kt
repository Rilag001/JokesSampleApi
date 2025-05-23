package com.example.jokesapi.jokeoverview.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jokesapi.R
import com.example.jokesapi.jokeoverview.JokesOverviewContract
import com.example.jokesapi.jokeoverview.JokesOverviewContract.Event
import com.example.jokesapi.shared.compose.JokeCard
import com.example.jokesapi.shared.compose.JokeErrorMessage
import com.example.jokesapi.shared.model.JokeType
import com.example.jokesapi.shared.model.JokeUi
import com.example.jokesapi.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JokeOverviewScreen(
    modifier: Modifier,
    state: JokesOverviewContract.State,
    onEvent: (Event) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                modifier = Modifier.background(Color.White),
                title = {
                    Text(text = stringResource(R.string.good_jokes), color = Color.Black)
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
                    JokesOverviewContract.State.Idle -> Unit
                    JokesOverviewContract.State.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(24.dp)
                                .align(Alignment.Center),
                        )
                    }
                    is JokesOverviewContract.State.Error -> {
                        Column(modifier = Modifier.align(Alignment.Center)) {
                            JokeErrorMessage(
                                modifier = Modifier,
                                messageRes = state.messageRes
                            )
                            Image(
                                modifier = Modifier
                                    .padding(top = 16.dp)
                                    .align(Alignment.CenterHorizontally)
                                    .size(50.dp)
                                    .clickable { onEvent(Event.OnRetryClicked) },
                                painter = painterResource(R.drawable.reload),
                                contentDescription = "Joke Image",
                                colorFilter = ColorFilter.tint(Color.Black)
                            )
                        }
                    }
                    is JokesOverviewContract.State.DisplayJokeCategories -> {
                        JokeList(
                            jokes = state.jokes,
                            randomJoke = state.randomJoke,
                            onEvent = onEvent,
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun JokeList(jokes: List<JokeType>, randomJoke: JokeUi, onEvent: (Event) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp),
    ) {
        item {
            SectionTitle(textRes = R.string.joke_categories)
        }
        items(count = jokes.size) {
            Row(
                modifier = Modifier
                    .background(Color(0xFFFCFBF8), RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onEvent(Event.OnJokeTypeClicked(jokes[it])) }
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp),
            ) {
                val imageRes = when(jokes[it]) {
                    JokeType.GENERAL -> R.drawable.general
                    JokeType.PROGRAMMING -> R.drawable.programming
                    JokeType.KNOCK_KNOCK -> R.drawable.knock
                }

                Image(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(50.dp),
                    painter = painterResource(imageRes),
                    contentDescription = "Joke Image",
                    colorFilter = ColorFilter.tint(Color.Black)
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 16.dp),
                    text = stringResource(R.string.x_joke, jokes[it].label.capitalize(Locale.current)),
                    color = Color.Black,
                    style = Typography.bodyLarge
                )
            }
        }
        item {
            SectionTitle(textRes = R.string.random_joke)
        }
        item {
            JokeCard(joke = randomJoke)
        }
    }
}

@Composable
private fun SectionTitle(@StringRes textRes: Int) {
    Text(
        modifier = Modifier.padding(top = 8.dp),
        text = stringResource(textRes),
        style = Typography.bodyLarge,
        color = Color.Black
    )
}

@Preview
@Composable
private fun PreviewJokeOverViewScreenDisplayJokeCategories() {
    val randomJoke = JokeUi(
        jokeType = JokeType.GENERAL,
        setup = "What kind of shoes does a thief wear?",
        punchline = "Sneakers"
    )
    JokeOverviewScreen(
        modifier = Modifier,
        state = JokesOverviewContract.State.DisplayJokeCategories(
            jokes = JokeType.entries,
            randomJoke = randomJoke,
        ),
        onEvent = {}
    )
}

@Preview
@Composable
private fun PreviewJokeOverViewScreenError() {
    JokeOverviewScreen(
        modifier = Modifier,
        state = JokesOverviewContract.State.Error(messageRes = R.string.error_network),
        onEvent = {}
    )
}