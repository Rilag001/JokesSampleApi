package com.example.jokesapi.jokeoverview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.jokesapi.jokecategory.JokesCategoryActivity
import com.example.jokesapi.jokeoverview.compose.JokeOverviewScreen
import com.example.jokesapi.shared.extensionFunctions.setTransparentEdgeToEdge
import com.example.jokesapi.ui.theme.JokesApiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JokesOverviewActivity : ComponentActivity() {

    private val viewModel: JokesOverviewViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTransparentEdgeToEdge()
        setContent {
            JokesApiTheme {
                val state by viewModel.state.collectAsStateWithLifecycle()
                JokeOverviewScreen(Modifier, state, viewModel::send)
            }
            SetUpSideEffects()
        }
    }

    @Composable
    fun SetUpSideEffects() {
        LaunchedEffect(Unit) {
            viewModel.sideEffect.collect { sideEffect ->
                when (sideEffect) {
                    is JokesOverviewContract.SideEffect.NavigateToJokeCategory -> {
                        startActivity(JokesCategoryActivity.makeIntent(
                            context = this@JokesOverviewActivity,
                            jokeType = sideEffect.jokeType
                        ))
                    }
                }
            }
        }
    }
}