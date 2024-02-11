package com.wassha.androidcodechallenge.di

import com.wassha.androidcodechallenge.data.JokeRepository
import com.wassha.androidcodechallenge.data.JokeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class JokeHiltModule {

    @Provides
    @Singleton
    fun getJokeRepository(jokeService: JokeService) = JokeRepository(jokeService)

    @Provides
    @Singleton
    fun getJokeService(): JokeService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .callTimeout(3, TimeUnit.SECONDS)
                .build()
        )
        .build()
        .create(JokeService::class.java)

    private companion object {
        const val BASE_URL = "https://v2.jokeapi.dev/joke/"
    }
}