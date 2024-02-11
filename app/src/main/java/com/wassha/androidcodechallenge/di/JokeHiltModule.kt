package com.wassha.androidcodechallenge.di

import com.wassha.androidcodechallenge.data.JokeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class JokeHiltModule {

    @Provides
    fun getJokeRepository() = JokeRepository()
}