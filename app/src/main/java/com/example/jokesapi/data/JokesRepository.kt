package com.example.jokesapi.data

import com.example.jokesapi.data.local.JokesLocalDataSource
import com.example.jokesapi.data.remote.JokesRemoteDataSource
import com.example.jokesapi.data.remote.model.JokeApi
import com.example.jokesapi.data.remote.model.JokesResult
import com.example.jokesapi.data.remote.model.JokesResultFailure
import com.example.jokesapi.data.remote.model.asResult
import com.example.jokesapi.shared.model.JokeType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.ZoneOffset

class JokesRepository(
    val ioDispatcher: CoroutineDispatcher,
    val localDataSource: JokesLocalDataSource,
    val remoteDataSource: JokesRemoteDataSource,
) {
    suspend fun getAllJokes(): JokesResult<List<JokeApi>> = withContext(ioDispatcher) {
        val localResult = localDataSource.getJokes()
        val now = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)

        if (localResult.isNotEmpty() && localResult.first().updatedAt + API_CALL_TIME_THRESHOLD_SECONDS > now) {
            JokesResult(data = localResult.map { it.toJokeApi() })
        } else {
            val remoteResult = remoteDataSource.getGoodJokes()
            if (remoteResult.data != null) {
                localDataSource.deleteJokes()
                localDataSource.insertJokes(jokes = remoteResult.data.map { it.toJokeEntity() })
            }
            remoteResult
        }
    }

    suspend fun getJokesByType(type: JokeType): JokesResult<List<JokeApi>> = withContext(ioDispatcher) {
        val localResult = localDataSource.getJokesByType(type = type)
        if (localResult.isNotEmpty()) {
            JokesResult(data = localResult.map { it.toJokeApi() })
        } else {
            JokesResultFailure.NotFound.asResult()
        }
    }

    companion object {
        private const val API_CALL_TIME_THRESHOLD_SECONDS = 600
    }
}