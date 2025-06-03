package com.example.jokesapi.shared.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jokesapi.shared.model.JokeType
import com.example.jokesapi.shared.model.JokeUi
import com.example.jokesapi.ui.theme.Typography

@Composable
fun JokeCard(joke: JokeUi) {
    Row(
        modifier = Modifier
            .background(Color(0xFFFCFBF8), RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .wrapContentSize()
            .clip(RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        JokeImage(jokeType = joke.jokeType)

        Column(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
                .padding(start = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(modifier = Modifier, text = joke.setup, style = Typography.bodyLarge, color = Color.Black)
            Text(modifier = Modifier, text = joke.punchline, style = Typography.bodyLarge, color = Color.Black)
        }
    }
}

@Preview
@Composable
private fun PreviewJokeCard() {
    JokeCard(
        joke = JokeUi(
            id = 1,
            setup = "Why did the chicken cross the road? Why did the chicken cross the road?",
            punchline = "To get to the other side!",
            jokeType = JokeType.GENERAL
        )
    )
}

@Preview
@Composable
private fun PreviewJokeCard2() {
    JokeCard(
        joke = JokeUi(
            id = 1,
            setup = "Why did the chicken cross the road? Why did the chicken cross the road?",
            punchline = "To get to the other side!",
            jokeType = JokeType.KNOCK_KNOCK
        )
    )
}

@Preview
@Composable
private fun PreviewJokeCard3() {
    JokeCard(
        joke = JokeUi(
            id = 1,
            setup = "Why did the chicken cross the road? Why did the chicken cross the road?",
            punchline = "To get to the other side!",
            jokeType = JokeType.PROGRAMMING
        )
    )
}