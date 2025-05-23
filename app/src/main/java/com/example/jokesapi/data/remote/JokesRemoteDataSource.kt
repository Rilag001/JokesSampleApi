package com.example.jokesapi.data.remote

import com.example.jokesapi.data.NetworkConnectivity
import com.example.jokesapi.data.remote.model.JokeApi
import com.example.jokesapi.data.remote.model.JokesResult
import com.example.jokesapi.data.remote.model.JokesResultFailure
import com.example.jokesapi.data.remote.model.asResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber

class JokesRemoteDataSource(
    val jokesService: JokesService,
    val ioDispatcher: CoroutineDispatcher,
    val networkConnectivity: NetworkConnectivity,
) {

    suspend fun getGoodJokes(): JokesResult<List<JokeApi>> = withContext(ioDispatcher) {
        try {
            if (!networkConnectivity.isConnected) {
                return@withContext JokesResultFailure.NetworkOffline.asResult()
            }

            val response = jokesService.getGoodJokes()
            if (response.isSuccessful) {
                return@withContext JokesResult(data = response.body() ?: emptyList())
            } else {
                Timber.e("Error fetching good jokes. Error code: ${response.code()}")
                return@withContext when (response.code()) {
                    HTTP_NOT_FOUND -> JokesResultFailure.NotFound.asResult()
                    else -> JokesResultFailure.ServerError(response.code()).asResult()
                }
            }
        } catch (e: Exception) {
            Timber.e(e, "Error fetching good jokes")
            return@withContext JokesResultFailure.UnexpectedError(e).asResult()
        }
    }

    companion object {
        private const val HTTP_NOT_FOUND = 404
    }
}