package com.whalescale.binit.view.custom_views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CircularLoaderView(color: Color) {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .width(50.dp)
            .height(50.dp)
            .padding(top = 40.dp)

    ) {
        CircularProgressIndicator(color = color)
    }
}