package com.ndteam.wasteandroidapp.view.main.screens.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ndteam.wasteandroidapp.R
import com.ndteam.wasteandroidapp.ui.theme.Inter
import com.ndteam.wasteandroidapp.ui.theme.Nunito
import com.ndteam.wasteandroidapp.view.game.DragTarget
import com.ndteam.wasteandroidapp.view.game.LongPressDraggable
import com.ndteam.wasteandroidapp.view.main.MainViewModel
import com.ndteam.wasteandroidapp.view.main.screens.search.SearchMainScreenContent
import kotlin.math.min


@Composable
fun GameMainScreen() {

    LaunchedEffect(Unit, block = {

    })

    GameMainScreenContent(

    )
}

@Composable
fun GameMainScreenContent() {
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
            .padding(start = 20.dp)
            .padding(top = 20.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ic_game_star),
                contentDescription = "Star icon",
                modifier = Modifier
                    .width(35.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                text = "1",
                modifier = Modifier.padding(horizontal = 8.dp),
                fontSize = 24.sp,
                fontFamily = Nunito,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        LongPressDraggable(modifier = Modifier.fillMaxSize()) {

            DragTarget(modifier = Modifier.size(130.dp).align(alignment = Alignment.TopCenter), dataToDrop = "Meat") {
                Image(
                    painter = painterResource(id = R.drawable.ic_game_item_meat),
                    contentDescription = "Game item",
                    modifier = Modifier

                        .width(50.dp),
                    contentScale = ContentScale.Fit
                )
            }



        }


        Row (modifier = Modifier
            .align(alignment = Alignment.BottomCenter)
            .padding(bottom = 5.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ic_def_organic_bin),
                contentDescription = "Organic bin",
                modifier = Modifier
                    .width(125.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.width(65.dp))

            Image(
                painter = painterResource(id = R.drawable.ic_def_recycle_bin),
                contentDescription = "Organic bin",
                modifier = Modifier
                    .width(125.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.width(65.dp))

            Image(
                painter = painterResource(id = R.drawable.ic_def_garbage_bin),
                contentDescription = "Organic bin",
                modifier = Modifier
                    .width(125.dp),
                contentScale = ContentScale.Fit
            )
        }

    }
}

@Preview(device = Devices.AUTOMOTIVE_1024p, widthDp = 1024, heightDp = 720)
@Composable
fun GameMainScreenContent_Preview() {
    GameMainScreenContent()
}