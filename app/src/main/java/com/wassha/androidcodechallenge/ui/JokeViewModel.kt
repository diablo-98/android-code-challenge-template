package com.wassha.androidcodechallenge.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wassha.androidcodechallenge.data.Joke
import com.wassha.androidcodechallenge.data.JokeRepository
import com.wassha.androidcodechallenge.data.JokeResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class JokeUiModel (val joke: String) {
    companion object {
        fun default() = JokeUiModel("Empty")
    }
}

fun Joke.toUiModel() = JokeUiModel(joke = value)

@HiltViewModel
class JokeViewModel @Inject constructor(
    private val repository: JokeRepository,
): ViewModel() {

    private val _joke = mutableStateOf(JokeUiModel.default())
    val joke: State<JokeUiModel> = _joke

    fun onResume() {
        viewModelScope.launch {
            when (val result = repository.fetchJoke()) {
                is JokeResult.Success -> _joke.value = result.joke.toUiModel()
                is JokeResult.Failure -> {
                    /*do nothing*/
                }
            }
        }
    }
}
