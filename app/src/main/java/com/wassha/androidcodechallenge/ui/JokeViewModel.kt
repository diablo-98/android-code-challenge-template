package com.wassha.androidcodechallenge.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wassha.androidcodechallenge.data.JokeRepository
import com.wassha.androidcodechallenge.data.JokeStatus
import com.wassha.androidcodechallenge.db.entities.JokeEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class JokeUiModel(
    val joke: String,
    val length: Int,
    val words: Int,
    val status: JokeStatus,
) {
    companion object {
        fun default() = JokeUiModel(
            joke = "Empty",
            length = 0,
            words = 0,
            status = JokeStatus.Offline,
        )
    }
}

fun JokeEntity.toUiModel() = JokeUiModel(
    joke = content,
    length = content.length,
    words = content.split("\\s+".toRegex()).count { it.isNotEmpty() },
    status = status,
)

@HiltViewModel
class JokeViewModel @Inject constructor(
    private val repository: JokeRepository,
): ViewModel() {
    @OptIn(FlowPreview::class)
    val loading = repository.loading.debounce(200)

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
