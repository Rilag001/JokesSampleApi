package com.example.jokesapi.shared.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.jokesapi.R
import com.example.jokesapi.shared.extensionFunctions.conditional
import com.example.jokesapi.shared.model.JokeType

@Composable
fun JokeImage(jokeType: JokeType) {
    val imageRes = when(jokeType) {
        JokeType.GENERAL -> R.drawable.general
        JokeType.PROGRAMMING -> R.drawable.programming
        JokeType.KNOCK_KNOCK -> R.drawable.knock
    }

    Image(
        modifier = Modifier
            .conditional(jokeType == JokeType.GENERAL) {
                scale(scaleX = -1f, scaleY = 1f)
            }
            .clip(RoundedCornerShape(8.dp))
            .size(100.dp),
        contentScale = ContentScale.Crop,
        painter = painterResource(imageRes),
        contentDescription = "Joke Image",
    )
}