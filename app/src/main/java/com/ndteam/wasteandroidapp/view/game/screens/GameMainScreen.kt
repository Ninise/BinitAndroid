package com.ndteam.wasteandroidapp.view.main.screens.game

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.debugInspectorInfo
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
import com.ndteam.wasteandroidapp.view.custom_views.shake
import com.ndteam.wasteandroidapp.view.game.DragTarget
import com.ndteam.wasteandroidapp.view.game.DropTarget
import com.ndteam.wasteandroidapp.view.main.MainViewModel
import com.ndteam.wasteandroidapp.view.main.screens.search.SearchMainScreenContent
import com.squareup.moshi.internal.Util
import kotlinx.coroutines.delay
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

            var mistakeOrganic = remember {
                mutableStateOf(false)
            }

            var mistakeRecycler = remember {
                mutableStateOf(false)
            }

            var mistakeGarbage = remember {
                mutableStateOf(false)
            }

            DropTarget<String>(
                modifier = Modifier
                    .padding(6.dp)

            ) { isInBound, draggedItem ->

                Utils.log("isInBound: ${isInBound}; draggedItem: ${draggedItem}")

                var image = if (isInBound) R.drawable.ic_correct_organic_bin else R.drawable.ic_def_organic_bin

                if (draggedItem != null) {
                    if (draggedItem == "Meat") {
                        image = R.drawable.ic_mistake_organic_bin
                        mistakeOrganic.value = true


                    }
                }

                LaunchedEffect(Unit) {
                    delay(1_000)
                    mistakeOrganic.value = false
                }

                Image(
                    painter = painterResource(id = image),
                    contentDescription = "Organic bin",
                    modifier = Modifier
                        .width(125.dp)
                        .height(125.dp)
                        .shake(mistakeOrganic),
                    contentScale = ContentScale.Fit
                )
            }



            Spacer(modifier = Modifier.width(65.dp))

            DropTarget<String>(
                modifier = Modifier
                    .padding(6.dp)

            ) { isInBound, draggedItem ->

                var image = if (isInBound) R.drawable.ic_correct_recycle_bin else R.drawable.ic_def_recycle_bin

                if (draggedItem != null) {
                    if (draggedItem == "Meat") {
                        image = R.drawable.ic_mistake_recycle_bin
                        mistakeRecycler.value = true
                        
                        LaunchedEffect(Unit) {
                            delay(1_000)
                            mistakeRecycler.value = false
                        }
                    }
                }

                Image(
                    painter = painterResource(id = image),
                    contentDescription = "Recycle bin",
                    modifier = Modifier
                        .width(125.dp)
                        .height(125.dp)
                        .shake(mistakeRecycler),
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
                    painter = painterResource(id = if (isInBound) R.drawable.ic_correct_garbage_bin else R.drawable.ic_def_garbage_bin),
                    contentDescription = "Garbage bin",
                    modifier = Modifier
                        .width(125.dp)
                        .height(125.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }



        DragTarget(modifier = Modifier.size(90.dp), dataToDrop = "Meat") {
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