package com.wassha.androidcodechallenge.data

import android.util.Log
import com.wassha.androidcodechallenge.db.dao.JokeDao
import com.wassha.androidcodechallenge.db.entities.JokeEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import retrofit2.awaitResponse
import java.io.IOException

class JokeRepository(
    private val jokeService: JokeService,
    private val jokeDao: JokeDao,
) {

    val joke = jokeDao.getJoke()

    suspend fun fetchJoke(coroutineContext: CoroutineDispatcher = Dispatchers.IO) =
        withContext(coroutineContext) {
            var success = false
            try {
                val response = jokeService.getSingleProgrammingJoke().awaitResponse()
                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        val joke = body.toJokeEntity()
                        jokeDao.insert(joke)
                        success = true
                    }
                }
            } catch (e: IOException) {
                Log.e(TAG, "Unable to execute API due to ${e.javaClass.name}: ${e.message}")
            }

            if (!success) {
                jokeDao.setStatus(JokeStatus.Offline)
            }
        }

    @Throws(JSONException::class)
    private fun String.toJokeEntity(): JokeEntity {
        val jokeString = JSONObject(this)
            .getString(JSON_KEY_JOKE)
        return JokeEntity(0, jokeString, JokeStatus.Online)
    }

    private companion object {
        const val TAG = "JokeRepository"
        const val JSON_KEY_JOKE = "joke"
    }
}
