package com.wassha.androidcodechallenge.data

import androidx.compose.ui.graphics.Color

enum class JokeStatus(val source: String, val color: Color) {
    Online("Network", Color.Unspecified),
    Offline("Local", Color.Red),
}