package com.example.jokesapi.data

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET

private val Context.connectivityManager: ConnectivityManager
    get() = this.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

sealed class NetworkConnectivity : StatusApi {

    private class NetworkStatus(private val context: Context) : NetworkConnectivity() {
        override val isConnected
            get() = context.connectivityManager
                .run {
                    getNetworkCapabilities(activeNetwork)
                }?.hasCapability(NET_CAPABILITY_INTERNET) == true
    }
    companion object {

        @Volatile
        private var instance: NetworkConnectivity? = null

        fun getInstance(context: Context): NetworkConnectivity =
            instance ?: synchronized(this) {
                instance ?: NetworkStatus(context = context).also { instance = it }
            }
    }
}

internal interface StatusApi {
    val isConnected: Boolean
}
