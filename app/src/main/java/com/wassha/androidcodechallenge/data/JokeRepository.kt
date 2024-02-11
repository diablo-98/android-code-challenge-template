package com.wassha.androidcodechallenge.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import retrofit2.awaitResponse


sealed interface JokeResult {

    data class Success(val joke: Joke): JokeResult
    data class Failure(val errMsg: String): JokeResult
}

class JokeRepository(private val jokeService: JokeService) {

    suspend fun fetchJoke(coroutineContext: CoroutineDispatcher = Dispatchers.IO): JokeResult =
        withContext(coroutineContext) {
            val response = jokeService.getSingleProgrammingJoke().awaitResponse()

            if (response.isSuccessful) {
                val body = response.body()
                    ?: return@withContext JokeResult.Failure("No body for successful response")
                try {
                    val joke = body.toJoke()
                    JokeResult.Success(joke)
                } catch (e: JSONException) {
                    JokeResult.Failure("Response body JSON error: ${e.message}")
                }
            } else {
                JokeResult.Failure(
                    "Failed response- code: ${response.code()}," +
                            " body: ${response.errorBody()?.string()}"
                )
            }
        }

    @Throws(JSONException::class)
    private fun String.toJoke(): Joke {
        val jokeString = JSONObject(this)
            .getString(JSON_KEY_JOKE)
        return Joke(jokeString)
    }

    private companion object {
        const val JSON_KEY_JOKE = "joke"
    }
}
