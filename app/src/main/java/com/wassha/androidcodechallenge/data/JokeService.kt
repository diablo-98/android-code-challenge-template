package com.wassha.androidcodechallenge.data

import retrofit2.Call
import retrofit2.http.GET


interface JokeService {

    @GET("Programming?type=single")
    fun getSingleProgrammingJoke(): Call<String>
}