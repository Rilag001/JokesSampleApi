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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.jokesapi.main.navigation.JokesScreen
import com.example.jokesapi.R
import com.example.jokesapi.jokeoverview.JokeOverviewListItem
import com.example.jokesapi.jokeoverview.JokesOverviewContract
import com.example.jokesapi.jokeoverview.JokesOverviewContract.Event
import com.example.jokesapi.jokeoverview.JokesOverviewViewModel
import com.example.jokesapi.shared.compose.JokeCard
import com.example.jokesapi.shared.compose.JokeErrorMessage
import com.example.jokesapi.shared.compose.JokeImage
import com.example.jokesapi.shared.model.JokeType
import com.example.jokesapi.shared.model.JokeUi
import com.example.jokesapi.ui.theme.Typography

@Composable
fun JokeOverviewScreen(
    navController: NavController,
    viewModel: JokesOverviewViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::send

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is JokesOverviewContract.SideEffect.NavigateToJokeCategory -> {
                    navController.navigate(
                        route = JokesScreen.JokeCategory(jokeType = sideEffect.jokeType),
                    )
                }
            }
        }
    }

    JokeOverviewScreenContent(
        modifier = Modifier,
        state = state,
        onEvent = onEvent,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun JokeOverviewScreenContent(
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
                                .size(48.dp)
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
                        JokeList(list = state.list, onEvent = onEvent)
                    }
                }
            }
        }
    )
}

@Composable
fun JokeList(list: List<JokeOverviewListItem>, onEvent: (Event) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp),
    ) {
        items(
            items = list,
            key = { item: JokeOverviewListItem -> item.id },
        ) { item: JokeOverviewListItem ->
            when (item) {
                is JokeOverviewListItem.Title -> {
                    SectionTitle(textRes = item.titleRes)
                }
                is JokeOverviewListItem.JokeTypeCard -> {
                    Row(
                        modifier = Modifier
                            .background(Color(0xFFFCFBF8), RoundedCornerShape(8.dp))
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onEvent(Event.OnJokeTypeClicked(item.jokeType)) }
                            .fillMaxWidth()
                            .padding(8.dp),
                    ) {
                        JokeImage(jokeType = item.jokeType)
                        Text(
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(start = 16.dp),
                            text = stringResource(R.string.x_joke, item.jokeType.label.capitalize(Locale.current)),
                            color = Color.Black,
                            style = Typography.bodyLarge
                        )
                    }
                }
                is JokeOverviewListItem.RandomJokeCard -> {
                    JokeCard(joke = item.randomJoke)
                }
            }
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
        id = 1,
        jokeType = JokeType.GENERAL,
        setup = "What kind of shoes does a thief wear?",
        punchline = "Sneakers"
    )

    val list = listOf(
        JokeOverviewListItem.Title(R.string.joke_categories),
        JokeOverviewListItem.JokeTypeCard(jokeType = JokeType.GENERAL),
        JokeOverviewListItem.JokeTypeCard(jokeType = JokeType.PROGRAMMING),
        JokeOverviewListItem.JokeTypeCard(jokeType = JokeType.KNOCK_KNOCK),
        JokeOverviewListItem.Title(R.string.random_joke),
        JokeOverviewListItem.RandomJokeCard(randomJoke = randomJoke)
    )

    JokeOverviewScreenContent(
        modifier = Modifier,
        state = JokesOverviewContract.State.DisplayJokeCategories(list = list),
        onEvent = {}
    )
}

@Preview
@Composable
private fun PreviewJokeOverViewScreenError() {
    JokeOverviewScreenContent(
        modifier = Modifier,
        state = JokesOverviewContract.State.Error(messageRes = R.string.error_network),
        onEvent = {}
    )
}

@Preview
@Composable
private fun PreviewJokeOverViewScreenLoading() {
    JokeOverviewScreenContent(
        modifier = Modifier,
        state = JokesOverviewContract.State.Loading,
        onEvent = {}
    )
}