package com.example.jokesapi.data.local

import com.example.jokesapi.data.local.database.JokesDao
import com.example.jokesapi.data.local.database.JokesEntity
import com.example.jokesapi.shared.model.JokeType

class JokesLocalDataSource(val jokesDao: JokesDao) {

    fun getJokes(): List<JokesEntity> {
        return jokesDao.getAllJokes()
    }

    fun getJokesByType(type: JokeType): List<JokesEntity> {
        return jokesDao.getJokesByType(type = type.label)
    }

    fun insertJokes(jokes: List<JokesEntity>) {
        deleteJokes()
        jokes.forEach {
            jokesDao.insertJoke(it)
        }
    }

    fun deleteJokes() {
        jokesDao.delete()
    }
}