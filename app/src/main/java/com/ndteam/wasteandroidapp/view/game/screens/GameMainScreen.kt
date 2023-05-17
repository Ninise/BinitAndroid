package com.ndteam.wasteandroidapp.view.main.screens.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ndteam.wasteandroidapp.R
import com.ndteam.wasteandroidapp.models.GameObject
import com.ndteam.wasteandroidapp.models.RecycleType
import com.ndteam.wasteandroidapp.ui.theme.Nunito
import com.ndteam.wasteandroidapp.utils.Utils
import com.ndteam.wasteandroidapp.view.custom_views.shake
import com.ndteam.wasteandroidapp.view.game.DragTarget
import com.ndteam.wasteandroidapp.view.game.DropTarget
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Make an object model - image,id,type +
// Make a start and end point of an object lifecycle
// Add dataset of objects that falling
// Give points for correct objects
// Add a dialog for a game explanation
// Add correct drop state


@Composable
fun GameMainScreen(gameSet: List<GameObject>) {

    LaunchedEffect(Unit, block = {

    })

    var gameObject = remember {
        mutableStateOf(gameSet[0])
    }


    var counter = remember {
        mutableStateOf(0)
    }

    DisposableEffect(Unit) {
        val delayJob = CoroutineScope(Dispatchers.Default).launch {
            delay(5_000)
            Utils.log("DROP NEW")
            gameObject = mutableStateOf(gameSet[1])

        }

        onDispose {
            delayJob.cancel()
        }
    }

    GameMainScreenContent(gameObject, counter)

    counter.value = counter.value++

    // state is not updating like this, learn how to do it properly
}

@Composable
fun GameMainScreenContent(gameObject: MutableState<GameObject>, counter: MutableState<Int>) {
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
                text = "${counter.value}",
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

            val mistakeOrganic = remember {
                mutableStateOf(false)
            }

            val mistakeRecycler = remember {
                mutableStateOf(false)
            }

            val mistakeGarbage = remember {
                mutableStateOf(false)
            }

            DropTarget<GameObject>(
                modifier = Modifier
                    .padding(6.dp)

            ) { isInBound, draggedItem ->

                var image = if (isInBound) R.drawable.ic_correct_organic_bin else R.drawable.ic_def_organic_bin

                if (draggedItem != null) {
                    if (draggedItem.type == RecycleType.ORGANIC) {
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

            DropTarget<GameObject>(
                modifier = Modifier
                    .padding(6.dp)

            ) { isInBound, draggedItem ->

                var image = if (isInBound) R.drawable.ic_correct_recycle_bin else R.drawable.ic_def_recycle_bin

                if (draggedItem != null) {
                    if (draggedItem.type == RecycleType.RECYCLE) {
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

            DropTarget<GameObject>(
                modifier = Modifier
                    .padding(6.dp)

            ) { isInBound, draggedItem ->

                var image = if (isInBound) R.drawable.ic_correct_garbage_bin else R.drawable.ic_def_garbage_bin

                if (draggedItem != null) {
                    if (draggedItem.type == RecycleType.GARBAGE) {
                        image = R.drawable.ic_mistake_garbage_bin
                        mistakeGarbage.value = true

                        LaunchedEffect(Unit) {
                            delay(1_000)
                            mistakeGarbage.value = false
                        }
                    }
                }

                Image(
                    painter = painterResource(id = image),
                    contentDescription = "Garbage bin",
                    modifier = Modifier
                        .width(125.dp)
                        .height(125.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }


        DragTarget(modifier = Modifier.size(90.dp), dataToDrop = gameObject) {
            Image(
                painter = painterResource(id = gameObject.value.image),
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
//    GameMainScreenContent()
}