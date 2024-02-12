package com.wassha.androidcodechallenge.data

import android.util.Log
import com.wassha.androidcodechallenge.db.dao.JokeDao
import com.wassha.androidcodechallenge.db.entities.JokeEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import retrofit2.awaitResponse
import java.io.IOException

class JokeRepository(
    private val jokeService: JokeService,
    private val jokeDao: JokeDao,
) {

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()
    val joke = jokeDao.getJoke()

    suspend fun fetchJoke(coroutineContext: CoroutineDispatcher = Dispatchers.IO) =
        withContext(coroutineContext) {
            var success = false
            try {
                _loading.update { true }

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
            } finally {
                _loading.update { false }
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
