package com.ndteam.wasteandroidapp.view.main.screens.game

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
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
import com.ndteam.wasteandroidapp.view.custom_views.shake
import com.ndteam.wasteandroidapp.view.game.DragTarget
import com.ndteam.wasteandroidapp.view.game.DropTarget
import com.ndteam.wasteandroidapp.view.game.LocalDragTargetInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Make an object model - image,id,type +
// Make a start and end point of an object lifecycle +
// Renew states correctly (object, bins) +
// Check animation of renewed obj +
// Add dataset of objects that falling +
// Give points for correct objects +
// After object released add end of animation check +
// After object added to the bin and renewed check animation +
// Add a dialog for a game explanation
// Add correct drop state ?


@Composable
fun GameMainScreen(gameSet: List<GameObject>, onBackPress: () -> Unit) {

    LaunchedEffect(Unit, block = {

    })

    var gameObject by remember {
        mutableStateOf(gameSet[0])
    }


    var counter by remember {
        mutableStateOf(0)
    }


    GameMainScreenContent(gameObject, counter, onEndOfObject = { correct ->
        gameObject = gameSet.random()
        if (correct) {
            counter++
        }
    }, onBackPress = onBackPress)

}

@Composable
fun GameMainScreenContent(gameObject: GameObject, counter: Int, onEndOfObject: (Boolean) -> Unit, onBackPress: () -> Unit) {

    val offsetY = remember { Animatable(0f) }
    val offsetX = remember { Animatable(200f) }

    val scope = rememberCoroutineScope()


    suspend fun renewObjPosition() {
        offsetY.snapTo(0f)
        offsetX.snapTo(listOf<Float>(500f, 900f, 1200f, 1500f).random())
    }

    @Composable
    fun updateDragObjData() {
        LocalDragTargetInfo.current.dragOffset = Offset.Zero
        LocalDragTargetInfo.current.dragPosition = Offset.Zero
        LocalDragTargetInfo.current.isDragging = false
        LocalDragTargetInfo.current.ready = false
    }

    fun startFallingAnimation() {
        scope.launch {
            offsetY.animateTo(
                targetValue = 1300f,
                animationSpec = tween(durationMillis = 5_000)
            ) {
                if (this.value == 1300f) {

                    onEndOfObject(false)

                    CoroutineScope(Dispatchers.Default).launch {

                        renewObjPosition()
                        startFallingAnimation()
                    }
                }
            }
        }

    }

    @Composable
    fun afterDropRenewObject(correct: Boolean) {
        updateDragObjData()

        LaunchedEffect(Unit) {
            renewObjPosition()
            startFallingAnimation()
        }

        LaunchedEffect(key1 = Unit, block = {
            onEndOfObject(correct)
        })
    }

    startFallingAnimation()

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
                onBackPress()
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
            
            Image(
                painter = painterResource(id = R.drawable.ic_game_star),
                contentDescription = "Star icon",
                modifier = Modifier
                    .padding(top = 4.dp)
                    .width(35.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                text = "$counter",
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

                var image = if (isInBound) R.drawable.ic_inbound_organic_bin else R.drawable.ic_def_organic_bin

                if (draggedItem != null) {

                    var correct = false

                    if (draggedItem.type == RecycleType.ORGANIC) {

                        // todo correct state and points
                        correct = true
                        image = R.drawable.ic_correct_organic_bin

                    } else {
                        image = R.drawable.ic_mistake_organic_bin
                        mistakeOrganic.value = true

                        LaunchedEffect(Unit) {
                            delay(1_000)
                            mistakeOrganic.value = false
                        }

                        image = R.drawable.ic_def_organic_bin

                    }

                    afterDropRenewObject(correct)
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

                var image = if (isInBound) R.drawable.ic_inbound_recycle_bin else R.drawable.ic_def_recycle_bin

                if (draggedItem != null) {

                    var correct = false

                    if (draggedItem.type == RecycleType.RECYCLE) {
                        // todo correct state and points
                        correct = true
                        image = R.drawable.ic_correct_recycle_bin
                    } else {
                        image = R.drawable.ic_mistake_recycle_bin
                        mistakeRecycler.value = true

                        LaunchedEffect(Unit) {
                            delay(1_000)
                            mistakeRecycler.value = false
                            image = R.drawable.ic_def_recycle_bin
                        }

                    }

                    afterDropRenewObject(correct)
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

                var image = if (isInBound) R.drawable.ic_inbound_garbage_bin else R.drawable.ic_def_garbage_bin

                if (draggedItem != null) {

                    var correct = false

                    if (draggedItem.type == RecycleType.GARBAGE) {

                        correct = true

                        // todo correct state
                        image = R.drawable.ic_correct_garbage_bin

                    } else {

                        image = R.drawable.ic_mistake_garbage_bin
                        mistakeGarbage.value = true

                        LaunchedEffect(Unit) {
                            delay(1_000)
                            mistakeGarbage.value = false
                        }


                    }


                    // todo add correct state
                    LaunchedEffect(key1 = "Renew state", block = {
                        delay(1_000)
                        image = R.drawable.ic_def_garbage_bin
                    })

                    afterDropRenewObject(correct)

                }

                Image(
                    painter = painterResource(id = image),
                    contentDescription = "Garbage bin",
                    modifier = Modifier
                        .width(125.dp)
                        .height(125.dp)
                        .shake(mistakeGarbage),
                    contentScale = ContentScale.Fit
                )
            }
        }


        DragTarget(modifier = Modifier.size(90.dp), dataToDrop = gameObject, offsetY = offsetY, offsetX = offsetX, objectFell = {
            onEndOfObject(false)

            CoroutineScope(Dispatchers.Default).launch {
                renewObjPosition()
                startFallingAnimation()
            }
        }) {
            Image(
                painter = painterResource(id = gameObject.image),
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