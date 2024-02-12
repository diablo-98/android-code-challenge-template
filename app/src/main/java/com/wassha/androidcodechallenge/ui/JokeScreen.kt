package com.wassha.androidcodechallenge.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wassha.androidcodechallenge.ui.composables.BoldValues
import com.wassha.androidcodechallenge.ui.composables.VerticalDivider

@Composable
fun JokeScreen(
    viewModel: JokeViewModel,
) {

    val loading by viewModel.loading.collectAsStateWithLifecycle(false)
    val joke: JokeUiModel by viewModel.joke.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        JokeContent(joke)

        Button(
            onClick = { viewModel.onResume() },
            enabled = !loading,
        ) {
            if (loading) {
                CircularProgressIndicator(modifier = Modifier.requiredHeight(IntrinsicSize.Min))
            }
            Text(text = "Fetch Joke", Modifier.padding(8.dp))
        }
    }
}

@Composable
private fun ColumnScope.JokeContent(joke: JokeUiModel) {
    Column(
        modifier = Modifier.Companion.weight(1f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterVertically
        )
    ) {
        Text(
            text = "Joke",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
        )

        Text(
            text = joke.joke,
            style = MaterialTheme.typography.bodyLarge
        )

        if (joke != JokeUiModel.default()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.Center,
            ) {
                val (len, status) = remember(joke) {
                    joke.length to joke.status
                }
                if (len > 80) {
                    BoldValues("Length:", len)
                    VerticalDivider()
                }

                BoldValues(text = "Words:", value = joke.words)

                VerticalDivider()

                BoldValues(text = "Data source:", value = status.source, color = status.color)
            }
        }
    }
}
