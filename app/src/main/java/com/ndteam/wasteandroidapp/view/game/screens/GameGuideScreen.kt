package com.ndteam.wasteandroidapp.view.game.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ndteam.wasteandroidapp.R
import com.ndteam.wasteandroidapp.ui.theme.IconsDark
import com.ndteam.wasteandroidapp.ui.theme.Inter
import com.ndteam.wasteandroidapp.ui.theme.MainOrange
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun GameGuideScreen(onPlayPress: () -> Unit, onBackPressed: () -> Unit) {

    LaunchedEffect(Unit, block = {

    })

    GameGuideScreenContent(onPlayPress = onPlayPress, onBackPressed = onBackPressed)

}

@Composable
fun GameGuideScreenContent(onPlayPress: () -> Unit, onBackPressed: () -> Unit) {

    val str1 = stringResource(R.string.game_guide_content_one)
    val str2 = stringResource(R.string.game_guide_content_two)

    val buttonStr1 = stringResource(R.string.got_it)
    val buttonStr2 = stringResource(R.string.play)

    val counter = remember { mutableStateOf(1) }
    val contentText = remember { mutableStateOf(str1) }
    val buttonText = remember { mutableStateOf(buttonStr1) }

    val offsetY = remember { Animatable(0f) }
    val offsetX = remember { Animatable(0f) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit, block = {
        offsetY.snapTo(-60f)
    })

    // todo drop all items to bottom edge
    // todo make padding between bins and dialog more
    // todo move person closer to the bin

    fun dropBanana() {
        scope.launch {
            launch {
                offsetY.animateTo(
                    targetValue = 300f,
                    animationSpec = tween(durationMillis = 3_000)
                )
            }
            offsetX.animateTo(
                targetValue = 250f,
                animationSpec = tween(durationMillis = 1_000)
            )
        }
    }

    Box (contentAlignment = Alignment.BottomCenter) {
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
                onBackPressed()
            }) {
                Image(
                    painter = painterResource(id = R.drawable.ic_game_back_arrow),
                    contentDescription = "Back icon",
                    modifier = Modifier
                        .size(45.dp, 45.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier
                .weight(1f)
                .background(color = Color.Magenta))

        }


        Row (modifier = Modifier
            .align(alignment = Alignment.BottomCenter)
            .padding(bottom = 5.dp)) {

            Box (
                modifier = Modifier.align(alignment = Alignment.Bottom)
                    ) {

                Image(
                    painter = painterResource(id = R.drawable.ic_game_item_banana),
                    contentDescription = "Banana",
                    modifier = Modifier
                        .align(alignment = Alignment.CenterEnd)
                        .offset {
                            IntOffset(
                                offsetX.value.roundToInt(),
                                offsetY.value.roundToInt()
                            )
                        }
                        .size(30.dp),
                    contentScale = ContentScale.Fit
                )

                Image(
                    painter = painterResource(id = R.drawable.ic_game_guide_guy),
                    contentDescription = "Guy bin",
                    modifier = Modifier
                        .padding(6.dp)
                        .height(280.dp)
                    ,
                    contentScale = ContentScale.Fit
                )


                
            }



            Column(
                modifier = Modifier.align(alignment = Alignment.Bottom)
            ) {


                Column (
                    modifier = Modifier
                        .background(color = Color.White, shape = RoundedCornerShape(size = 10.dp))
                        .padding(all = 14.dp)
                        .alpha(0.9f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        buildAnnotatedString {
                            withStyle(style = ParagraphStyle(lineHeight = 30.sp)) {
                                withStyle(style = SpanStyle(
                                    color = MainOrange,
                                    fontFamily = Inter,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 18.sp)) {
                                    append("${counter.value}")
                                }

                                withStyle(
                                    style = SpanStyle(
                                        color = IconsDark,
                                        fontFamily = Inter,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 18.sp
                                    )
                                ) {
                                    append("/2")
                                }
                            }
                        }

                    )

                    Text(
                        text = contentText.value,
                        color = IconsDark,
                        textAlign = TextAlign.Center,
                        fontFamily = Inter,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(top = 10.dp)
                    )

                    Button(
                        onClick = {

                            if (counter.value == 2) {
                                onPlayPress()
                            }

                            counter.value++
                            contentText.value = str2
                            buttonText.value = buttonStr2

                            dropBanana()

                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = MainOrange),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .height(45.dp)) {

                        Text(
                            text = buttonText.value,
                            color = Color.White,
                            fontFamily = Inter,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(
                                horizontal = 10.dp
                            )
                        )

                    }
                }

                Spacer(
                    modifier = Modifier.height(30.dp)
                )

                Row {
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


    }
}
@Preview(device = Devices.AUTOMOTIVE_1024p, widthDp = 1024, heightDp = 720)
@Composable
fun GameGuideScreenContent_Preview() {
    GameGuideScreenContent(onBackPressed = {}, onPlayPress = {})
}