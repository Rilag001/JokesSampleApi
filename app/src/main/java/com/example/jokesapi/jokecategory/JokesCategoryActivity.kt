package com.example.jokesapi.jokecategory

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.jokesapi.jokecategory.compose.JokesCategoryScreen
import com.example.jokesapi.shared.extensionFunctions.getSerializableCompat
import com.example.jokesapi.shared.model.JokeType
import com.example.jokesapi.shared.extensionFunctions.setTransparentEdgeToEdge
import com.example.jokesapi.ui.theme.JokesApiTheme
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable

@AndroidEntryPoint
class JokesCategoryActivity : ComponentActivity() {

    companion object {
        const val EXTRA_JOKE_TYPE = "EXTRA_JOKE_TYPE"

        fun makeIntent(
            context: Context,
            jokeType: JokeType,
        ): Intent {
            return Intent(context, JokesCategoryActivity::class.java).apply {
                putExtra(EXTRA_JOKE_TYPE, jokeType)
            }
        }
    }

    private val viewModel: JokesCategoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTransparentEdgeToEdge()
        setContent {
            JokesApiTheme {
                val state by viewModel.state.collectAsStateWithLifecycle()
                JokesCategoryScreen(Modifier, state, viewModel::send)

                SetUpSideEffects()
            }
        }

        val jokeType = intent.getSerializableCompat<JokeType>(EXTRA_JOKE_TYPE) ?: JokeType.GENERAL
        viewModel.send(JokesCategoryContract.Event.Init(jokeType = jokeType))
    }

    @Composable
    private fun SetUpSideEffects() {
        LaunchedEffect(Unit) {
            viewModel.sideEffect.collect { sideEffect ->
                when (sideEffect) {
                    is JokesCategoryContract.SideEffect.NavigateBack -> {
                        finish()
                    }
                }
            }
        }
    }
}