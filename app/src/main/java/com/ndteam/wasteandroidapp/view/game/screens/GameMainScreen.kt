package com.ndteam.wasteandroidapp.view.main.screens.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
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
import com.ndteam.wasteandroidapp.utils.Utils
import com.ndteam.wasteandroidapp.view.game.DragTarget
import com.ndteam.wasteandroidapp.view.game.DropTarget
import com.ndteam.wasteandroidapp.view.main.MainViewModel
import com.ndteam.wasteandroidapp.view.main.screens.search.SearchMainScreenContent
import com.squareup.moshi.internal.Util
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



        Row (modifier = Modifier
            .align(alignment = Alignment.BottomCenter)
            .padding(bottom = 5.dp)) {

            DropTarget<String>(
                modifier = Modifier
                    .padding(6.dp)

            ) { isInBound, draggedItem ->

                Utils.log("isInBound: ${isInBound}; draggedItem: ${draggedItem}")


                Image(
                    painter = painterResource(id = R.drawable.ic_def_organic_bin),
                    contentDescription = "Organic bin",
                    modifier = Modifier
                        .width(125.dp),
                    contentScale = ContentScale.Fit
                )
            }



            Spacer(modifier = Modifier.width(65.dp))

            DropTarget<String>(
                modifier = Modifier
                    .padding(6.dp)

            ) { isInBound, draggedItem ->

                Utils.log("isInBound: ${isInBound}; draggedItem: ${draggedItem}")

                Image(
                    painter = painterResource(id = R.drawable.ic_def_recycle_bin),
                    contentDescription = "Organic bin",
                    modifier = Modifier
                        .width(125.dp),
                    contentScale = ContentScale.Fit
                )

            }

            Spacer(modifier = Modifier.width(65.dp))

            DropTarget<String>(
                modifier = Modifier
                    .padding(6.dp)

            ) { isInBound, draggedItem ->

                Utils.log("isInBound: ${isInBound}; draggedItem: ${draggedItem}")


                Image(
                    painter = painterResource(id = R.drawable.ic_def_garbage_bin),
                    contentDescription = "Organic bin",
                    modifier = Modifier
                        .width(125.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }



        DragTarget(modifier = Modifier.size(90.dp).align(alignment = Alignment.TopCenter), dataToDrop = "Meat") {
            Image(
                painter = painterResource(id = R.drawable.ic_game_item_meat),
                contentDescription = "Game item",
                modifier = Modifier

                    .width(50.dp),

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

@Composable
fun SpringRelease(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Circle(modifier = Modifier
            .background(Color.Green, CircleShape))
    }
}
@Composable
fun Circle(modifier: Modifier) {
    Box(
        modifier = modifier
            .size(50.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(Icons.Default.Star, contentDescription = null, tint = Color.White)
    }
}