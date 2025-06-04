package com.example.jokesapi.shared.compose

import androidx.annotation.StringRes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.jokesapi.R
import com.example.jokesapi.ui.theme.JokesApiTheme

@Composable
fun JokeErrorMessage(modifier: Modifier, @StringRes messageRes: Int) {
    Text(
        modifier = modifier,
        text = stringResource(messageRes),
        style = JokesApiTheme.typography.body,
    )
}

@Preview
@Composable
private fun PreviewErrorMessage() {
    JokeErrorMessage(modifier = Modifier, messageRes = R.string.error_network)
}