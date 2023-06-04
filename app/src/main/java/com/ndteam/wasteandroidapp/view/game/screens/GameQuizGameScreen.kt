package com.ndteam.wasteandroidapp.view.game.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ndteam.wasteandroidapp.R
import com.ndteam.wasteandroidapp.ui.theme.IconsDark
import com.ndteam.wasteandroidapp.ui.theme.Inter
import com.ndteam.wasteandroidapp.ui.theme.MainOrange
import com.ndteam.wasteandroidapp.ui.theme.Nunito
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun GameQuizGameScreen(onBackPress: () -> Unit) {

    val counter by remember {
        mutableStateOf(0)
    }

    val currentQuestion by remember {
        mutableStateOf(1)
    }

    GameQuizGameScreenContent(counter = counter, currentQuestion = currentQuestion, onBackPress = onBackPress)
}

@Composable
fun GameQuizGameScreenContent(counter: Int, currentQuestion: Int, onBackPress: () -> Unit) {

    val starPuls = remember { mutableStateOf(false) }

    val stateA = remember { mutableStateOf(false) }
    val stateB = remember { mutableStateOf(false) }
    val stateC = remember { mutableStateOf(false) }
    val stateD = remember { mutableStateOf(false) }

    fun renewState() {
        stateA.value = false
        stateB.value = false
        stateC.value = false
        stateD.value = false
    }

    val infiniteTransition = rememberInfiniteTransition()

    val scale by infiniteTransition.animateFloat(
        initialValue = -5f,
        targetValue = -800f,
        animationSpec = infiniteRepeatable(
            animation = tween(25_000),
            repeatMode = RepeatMode.Reverse
        )
    )


    fun increaseStars() {
        starPuls.value = true

        CoroutineScope(Dispatchers.Default).launch {
            delay(1_000)
            starPuls.value = false
        }
    }



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

        Column {
            Row(modifier = Modifier
                .padding(top = 10.dp, start = 20.dp, end = 20.dp)) {

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

                Spacer(modifier = Modifier
                    .weight(1f)
                    .background(color = Color.Magenta))

                Row (modifier = Modifier.align(alignment = Alignment.CenterVertically)) {

                    PulsatingAnimation(selected = starPuls.value) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_game_star),
                            contentDescription = "Star icon",
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .width(35.dp),
                            contentScale = ContentScale.Fit
                        )
                    }

                    Text(
                        text = "$counter",
                        modifier = Modifier.padding(horizontal = 8.dp),
                        fontSize = 24.sp,
                        fontFamily = Nunito,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Box (
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 160.dp)
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
                                    fontSize = 18.sp)
                                ) {
                                    append("$currentQuestion") // todo add mutable
                                }

                                withStyle(
                                    style = SpanStyle(
                                        color = IconsDark,
                                        fontFamily = Inter,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 18.sp
                                    )
                                ) {
                                    append("/5") // todo add max amount
                                }
                            }
                        }

                    )

                    Text(
                        text = "Which of the following items should be sorted into the \"Recycle\" bin?",
                        color = IconsDark,
                        textAlign = TextAlign.Center,
                        fontFamily = Inter,
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(top = 10.dp)
                    )

                    AnswerButton(text = "a) Plastic bottles", state = stateA.value, onClick = {
                        renewState()
                        stateA.value = !stateA.value
                        increaseStars()
                    })
                    AnswerButton(text = "b) Pizza boxes", state = stateB.value, onClick = {
                        renewState()
                        stateB.value = !stateB.value
                    })
                    AnswerButton(text = "c) Food scraps", state = stateC.value, onClick = {
                        renewState()
                        stateC.value = !stateC.value
                    })
                    AnswerButton(text = "d) Soiled paper towels", state = stateD.value, onClick = {
                        renewState()
                        stateD.value = !stateD.value
                    })

                    Spacer(modifier = Modifier.height(5.dp))

                }
            }


        }
    }
}

@Composable
fun AnswerButton(text: String, state: Boolean, onClick: () -> Unit) {



    Button(
        onClick = {
            onClick()
        },
        colors = ButtonDefaults.buttonColors(backgroundColor = if (!state) Color.White else MainOrange),
        shape = RoundedCornerShape(6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .padding(horizontal = 20.dp)
            .height(55.dp)) {

        Text(
            text = text,
            color = if (!state) IconsDark else Color.White,
            fontFamily = Inter,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth()
        )

    }
}

@Composable
fun PulsatingAnimation(selected: Boolean, content: @Composable () -> Unit) {

    val scale = animateFloatAsState(if (selected) 1.4f else 1f)

    Box(modifier = Modifier.scale(scale.value)) {
        content()
    }

}

@Preview
@Composable
fun GameQuizGameScreenContent_Preview() {


    GameQuizGameScreenContent(counter = 0, currentQuestion = 1, onBackPress = {

    })
}