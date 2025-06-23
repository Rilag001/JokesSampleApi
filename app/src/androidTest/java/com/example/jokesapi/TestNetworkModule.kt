package com.example.jokesapi

import com.example.jokesapi.di.NetworkModule
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
class TestNetworkModule : NetworkModule() {

    override fun baseUrl(): HttpUrl {
        return "http://127.0.0.1:8080/".toHttpUrl()
    }
}