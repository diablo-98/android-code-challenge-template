package com.wassha.androidcodechallenge.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun JokeScreen(
    viewModel: JokeViewModel
) {

    val joke: JokeUiModel by viewModel.joke.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterVertically
        )
    ) {

        Text(
            text = "Joke",
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = joke.joke,
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = "Length: ${joke.joke.length}",
            style = MaterialTheme.typography.bodySmall,
        )

        Text(
            text = "Words: ${joke.joke.split("\\s+".toRegex())
                .filter { it.isNotEmpty() }
                .count()}",
            style = MaterialTheme.typography.bodySmall,
        )

        Text(
            text = "Data was fetched from local",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Red
        )

        Button(onClick = { viewModel.onResume() }) {
            Text(text = "Fetch Joke")
        }
    }
}
