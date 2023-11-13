package com.wassha.androidcodechallenge.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wassha.androidcodechallenge.data.Joke
import com.wassha.androidcodechallenge.data.JokeRepository

data class JokeUiModel (val joke: String) {
    companion object {
        fun default() = JokeUiModel("Empty")
    }
}

fun Joke.toUiModel() = JokeUiModel(joke = value)

class JokeViewModel(
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

class JokeViewModelFactory(
    private val repository: JokeRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JokeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return JokeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
