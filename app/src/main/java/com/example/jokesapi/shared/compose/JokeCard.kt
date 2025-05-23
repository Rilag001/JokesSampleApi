package com.example.jokesapi.shared.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jokesapi.R
import com.example.jokesapi.shared.model.JokeType
import com.example.jokesapi.shared.model.JokeUi
import com.example.jokesapi.ui.theme.Typography

@Composable
fun JokeCard(joke: JokeUi) {
    Row(
        modifier = Modifier
            .background(Color(0xFFFCFBF8), RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        val imageRes = when(joke.jokeType) {
            JokeType.GENERAL -> R.drawable.general
            JokeType.PROGRAMMING -> R.drawable.programming
            JokeType.KNOCK_KNOCK -> R.drawable.knock
        }

        Image(
            modifier = Modifier
                .size(50.dp),
            painter = painterResource(imageRes),
            contentDescription = "Joke Image",
            colorFilter = ColorFilter.tint(Color.Black)
        )

        Column(
            modifier = Modifier.padding(start = 24.dp),
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
            setup = "Why did the chicken cross the road?",
            punchline = "To get to the other side!",
            jokeType = JokeType.GENERAL
        )
    )
}