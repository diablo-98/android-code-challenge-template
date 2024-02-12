package com.wassha.androidcodechallenge.ui.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

val boldValueStyle
    @Composable
    get() = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)

@Composable
fun BoldValues(text: String, value: Any, color: Color = Color.Unspecified) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall,
    )
    Text(
        text = "$value",
        style = boldValueStyle,
        color = color,
    )
}