package com.example.jokesapi.data.remote

import com.example.jokesapi.data.remote.model.JokeApi
import retrofit2.Response
import retrofit2.http.GET

interface JokesService {

    @GET("goodJokes/")
    suspend fun getGoodJokes(): Response<List<JokeApi>>
}