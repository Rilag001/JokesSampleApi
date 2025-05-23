package com.example.jokesapi.shared.extensionFunctions

import android.content.Intent
import android.os.Build
import android.os.Bundle
import java.io.Serializable

@Suppress("DEPRECATION", "UNCHECKED_CAST")
fun <T : Serializable> Bundle.getSerializableCompat(name: String, clazz: Class<T>): T? {
    return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {
        getSerializable(name, clazz)
    } else {
        getSerializable(name) as? T
    }
}

inline fun <reified T : Serializable> Bundle.getSerializableCompat(name: String): T? =
    getSerializableCompat(name, T::class.java)

inline fun <reified T : Serializable> Intent.getSerializableCompat(name: String): T? =
    extras?.getSerializableCompat(name)