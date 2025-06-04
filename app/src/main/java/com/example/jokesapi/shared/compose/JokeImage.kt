package com.example.jokesapi.shared.compose

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.jokesapi.R
import com.example.jokesapi.shared.extensionFunctions.conditional
import com.example.jokesapi.shared.model.JokeType

@Composable
fun JokeImage(jokeType: JokeType) {
    var enabled by remember {
        mutableStateOf(false)
    }

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
            .conditional(jokeType == JokeType.GENERAL) {
                shake(enabled)
            }
            .clip(RoundedCornerShape(8.dp))
            .size(100.dp)
            .clickable { enabled = !enabled }
        ,
        contentScale = ContentScale.Crop,
        painter = painterResource(imageRes),
        contentDescription = "Joke Image",
    )
}

fun Modifier.shake(enabled: Boolean) = composed(
    factory = {
        val scale by animateFloatAsState(
            targetValue = if (enabled) 1f else 0.9f,
            animationSpec = repeatable(
                iterations = 6,
                animation = tween(durationMillis = 50, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            )
        )

        Modifier.graphicsLayer {
            scaleX = if (enabled) scale else 1f
            scaleY = if (enabled) scale else 1f
        }
    },
    inspectorInfo = debugInspectorInfo {
        name = "shake"
        properties["enabled"] = enabled
    }
)