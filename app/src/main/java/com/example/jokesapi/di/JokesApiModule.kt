package com.example.jokesapi.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.jokesapi.data.JokesRepository
import com.example.jokesapi.data.NetworkConnectivity
import com.example.jokesapi.data.local.JokesLocalDataSource
import com.example.jokesapi.data.local.database.JokesDatabase
import com.example.jokesapi.data.remote.JokesRemoteDataSource
import com.example.jokesapi.data.remote.JokesService
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class JokesApiModule {

    companion object {
        private const val BASE_URL = "https://api.sampleapis.com/jokes/"
    }

    @Reusable
    @Provides
    fun provideApplicationContext(application: Application): Context = application

    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logger = HttpLoggingInterceptor()
        logger.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClientBuilder = OkHttpClient.Builder()
            .addInterceptor(logger)

        return okHttpClientBuilder.build()
    }

    @Provides
    fun provideJokesLocalDataSource(context: Context): JokesLocalDataSource {
        val jokesDao = Room.databaseBuilder(
            context,
            JokesDatabase::class.java, "database-jokes"
        ).build().jokesDao()
        return JokesLocalDataSource(jokesDao = jokesDao)
    }

    @Provides
    fun provideJokesRemoteDataSource(
        retrofit: Retrofit,
        dispatcher: CoroutineDispatcher,
        networkConnectivity: NetworkConnectivity,
    ): JokesRemoteDataSource {
        val jokesService: JokesService = retrofit.create(JokesService::class.java)
        return JokesRemoteDataSource(
            jokesService = jokesService,
            ioDispatcher = dispatcher,
            networkConnectivity = networkConnectivity
        )
    }

    @Provides
    fun provideStarWarsRepository(
        dispatcher: CoroutineDispatcher,
        localDataSource: JokesLocalDataSource,
        remoteDataSource: JokesRemoteDataSource,
    ): JokesRepository {
        return JokesRepository(
            ioDispatcher = dispatcher,
            localDataSource = localDataSource,
            remoteDataSource = remoteDataSource,
        )
    }

    @Reusable
    @Provides
    fun provideNetworkConnectivity(context: Context): NetworkConnectivity =
        NetworkConnectivity.getInstance(context = context)
}