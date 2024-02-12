package com.wassha.androidcodechallenge.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wassha.androidcodechallenge.data.JokeStatus
import com.wassha.androidcodechallenge.db.entities.JokeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JokeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(joke: JokeEntity)

    @Query("UPDATE jokes SET status = :status WHERE id == 0")
    suspend fun setStatus(status: JokeStatus)

    @Query("SELECT * FROM jokes LIMIT 1")
    fun getJoke(): Flow<JokeEntity?>
}