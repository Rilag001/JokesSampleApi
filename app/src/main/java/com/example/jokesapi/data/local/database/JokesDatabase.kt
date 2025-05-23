package com.example.jokesapi.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [JokesEntity::class], version = 1)
abstract class JokesDatabase : RoomDatabase() {
    abstract fun jokesDao(): JokesDao
}