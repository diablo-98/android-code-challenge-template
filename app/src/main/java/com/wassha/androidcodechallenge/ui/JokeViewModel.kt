package com.wassha.androidcodechallenge.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wassha.androidcodechallenge.data.Joke
import com.wassha.androidcodechallenge.data.JokeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class JokeUiModel (val joke: String) {
    companion object {
        fun default() = JokeUiModel("Empty")
    }
}

fun Joke.toUiModel() = JokeUiModel(joke = value)

@HiltViewModel
class JokeViewModel @Inject constructor(
    private val repository: JokeRepository
): ViewModel() {

    val joke = MutableLiveData<JokeUiModel>()

    init {
        joke.value = JokeUiModel.default()
    }

    fun onResume() {
        repository.fetchJoke().let { joke.value = it.toUiModel() }
    }

}
