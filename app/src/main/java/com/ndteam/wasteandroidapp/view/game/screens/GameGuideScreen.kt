package com.ndteam.wasteandroidapp.view.game.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ndteam.wasteandroidapp.R

@Composable
fun GameGuideScreen(onPlayPress: () -> Unit) {

    LaunchedEffect(Unit, block = {

    })


    GameGuideScreenContent(onPlayPress = onPlayPress)

}

@Composable
fun GameGuideScreenContent(onPlayPress: () -> Unit) {


    Box {
        Image(
            painter = painterResource(id = R.drawable.ic_game_background),
            contentDescription = "Game background",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Row(modifier = Modifier
            .align(alignment = Alignment.TopStart)
            .padding(top = 10.dp, start = 25.dp, end = 25.dp)) {

            IconButton(onClick = {

            }) {
                Image(
                    painter = painterResource(id = R.drawable.ic_game_back_arrow),
                    contentDescription = "Back icon",
                    modifier = Modifier
                        .size(45.dp, 45.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.weight(1f).background(color = Color.Magenta))

        }

        Row (modifier = Modifier
            .align(alignment = Alignment.BottomCenter)
            .padding(bottom = 5.dp)) {

            Image(
                painter = painterResource(id = R.drawable.ic_game_guide_guy),
                contentDescription = "Guy bin",
                modifier = Modifier
                    .padding(6.dp)
                    .height(280.dp),
                contentScale = ContentScale.Fit
            )

            Image(
                painter = painterResource(id = R.drawable.ic_def_organic_bin),
                contentDescription = "Organic bin",
                modifier = Modifier
                    .padding(6.dp)
                    .width(125.dp)
                    .height(125.dp)
                    .align(alignment = Alignment.Bottom),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.width(65.dp))

            Image(
                painter = painterResource(id = R.drawable.ic_def_recycle_bin),
                contentDescription = "Organic bin",
                modifier = Modifier
                    .padding(6.dp)
                    .width(125.dp)
                    .height(125.dp)
                    .align(alignment = Alignment.Bottom),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.width(65.dp))

            Image(
                painter = painterResource(id = R.drawable.ic_def_garbage_bin),
                contentDescription = "Organic bin",
                modifier = Modifier
                    .padding(6.dp)
                    .width(125.dp)
                    .height(125.dp)
                    .align(alignment = Alignment.Bottom),
                contentScale = ContentScale.Fit
            )


        }

    }
}

@Preview(device = Devices.AUTOMOTIVE_1024p, widthDp = 1024, heightDp = 720)
@Composable
fun GameGuideScreenContent_Preview() {
    GameGuideScreenContent {

    }
}