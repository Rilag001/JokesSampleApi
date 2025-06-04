package com.example.jokesapi.data.local.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.time.ZoneOffset

@Entity(tableName = "good_jokes")
data class JokesEntity(
    @PrimaryKey
    @ColumnInfo("id") val id: Int,
    @ColumnInfo("type") val type: String,
    @ColumnInfo("setup") val setup: String,
    @ColumnInfo("punchline") val punchline: String,
    @ColumnInfo(name = "updated_at")
    val updatedAt: Long = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
)