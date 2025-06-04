package com.example.jokesapi.main.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.jokesapi.jokecategory.compose.JokesCategoryScreen
import com.example.jokesapi.jokeoverview.compose.JokeOverviewScreen

@Composable
fun JokeNavigationStack() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = JokesScreen.JokeOverview,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(400)) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(400)) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(400)) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(400)) }
    ) {
        composable<JokesScreen.JokeOverview> {
            JokeOverviewScreen(navController = navController)
        }
        composable<JokesScreen.JokeCategory> {backStackEntry ->
            val jokeCategory: JokesScreen.JokeCategory = backStackEntry.toRoute()
            JokesCategoryScreen(
                navController = navController,
                jokeType = jokeCategory.jokeType,
            )
        }
    }
}