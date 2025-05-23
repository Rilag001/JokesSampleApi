package com.example.jokesapi.shared.compose

import androidx.annotation.StringRes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.jokesapi.R

@Composable
fun JokeErrorMessage(modifier: Modifier, @StringRes messageRes: Int) {
    Text(
        modifier = modifier,
        text = stringResource(messageRes),
        fontSize = 20.sp,
    )
}

@Preview
@Composable
private fun PreviewErrorMessage() {
    JokeErrorMessage(modifier = Modifier, messageRes = R.string.error_network)
}