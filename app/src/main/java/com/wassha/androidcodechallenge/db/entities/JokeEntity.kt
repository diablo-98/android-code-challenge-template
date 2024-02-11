package com.wassha.androidcodechallenge.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wassha.androidcodechallenge.data.JokeStatus

@Entity(tableName = "jokes")
data class JokeEntity(
    @PrimaryKey
    val id: Int,
    val content: String,
    val status: JokeStatus,
)
