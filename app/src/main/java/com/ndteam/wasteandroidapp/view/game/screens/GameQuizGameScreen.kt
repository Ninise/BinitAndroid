package com.ndteam.wasteandroidapp.view.game.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ndteam.wasteandroidapp.R
import kotlin.random.Random

@Composable
fun GameQuizGameScreen() {
    GameQuizGameScreenContent()
}

@Composable
fun GameQuizGameScreenContent() {

    val infiniteTransition = rememberInfiniteTransition()

    val scale by infiniteTransition.animateFloat(
        initialValue = -5f,
        targetValue = -800f,
        animationSpec = infiniteRepeatable(
            animation = tween(15_000),
            repeatMode = RepeatMode.Reverse
        )
    )


    Box(
        modifier = Modifier.fillMaxSize()
    ) {
      Row(modifier = Modifier.horizontalScroll(state = rememberScrollState(), enabled = true)) {
          Image(
              painter = painterResource(id = R.drawable.ic_game_quiz_background),
              contentDescription = "Screen back",
              contentScale = ContentScale.FillHeight,
              modifier = Modifier
                  .fillMaxWidth()
                  .offset(x = scale.dp)

          )
      }
    }
}


@Composable
fun GameQuizGameScreenContent_Preview() {
    GameQuizGameScreenContent()
}