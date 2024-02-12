package com.wassha.androidcodechallenge.ui.composables

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


val verticalDividerModifier = Modifier
    .fillMaxHeight()
    .padding(horizontal = 4.dp)
    .width(2.dp)

@Composable
fun VerticalDivider() {
    Divider(modifier = verticalDividerModifier)
}