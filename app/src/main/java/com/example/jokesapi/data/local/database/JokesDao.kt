package com.example.jokesapi.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
abstract class JokesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertJoke(jokeEntity: JokesEntity)

    @Query("SELECT * FROM good_jokes")
    abstract fun getAllJokes(): List<JokesEntity>

    @Query("SELECT * FROM good_jokes WHERE type LIKE :type")
    abstract fun getJokesByType(type: String): List<JokesEntity>

    @Query("DELETE FROM good_jokes")
    abstract fun delete()
}