package com.wassha.androidcodechallenge.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wassha.androidcodechallenge.data.JokeRepository
import com.wassha.androidcodechallenge.db.entities.JokeEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class JokeUiModel (val joke: String) {
    companion object {
        fun default() = JokeUiModel("Empty")
    }
}

fun JokeEntity.toUiModel() = JokeUiModel(joke = content)

@HiltViewModel
class JokeViewModel @Inject constructor(
    private val repository: JokeRepository,
): ViewModel() {

    val joke = repository.joke
        .map {
            it.toUiModel()
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), JokeUiModel.default())

    fun onResume() {
        viewModelScope.launch {
            repository.fetchJoke()
        }
    }
}
