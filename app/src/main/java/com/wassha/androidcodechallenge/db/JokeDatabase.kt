package com.wassha.androidcodechallenge.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wassha.androidcodechallenge.db.dao.JokeDao
import com.wassha.androidcodechallenge.db.entities.JokeEntity


@Database(
    entities = [
        JokeEntity::class
    ],
    exportSchema = true,
    version = 10000,
)
abstract class JokeDatabase: RoomDatabase() {
    abstract fun jokeDao(): JokeDao
}