package com.whalescale.binit.view.game.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whalescale.binit.R
import com.whalescale.binit.models.AnswerObject
import com.whalescale.binit.models.QuizObject
import com.whalescale.binit.ui.theme.*
import com.whalescale.binit.utils.GameUtils
import com.whalescale.binit.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun GameQuizGameScreen(gameSet: MutableList<QuizObject>, onBackPress: () -> Unit, onFinish: (Int) -> Unit) {

    var counter by remember {
        mutableStateOf(0)
    }

    var currentQuestion by remember {
        mutableStateOf(gameSet.removeFirst())
    }

    Utils.log("gameSet ${gameSet.size}")

    GameQuizGameScreenContent(counter = counter, questionNumber = GameUtils.QUIZ_AMOUNT - gameSet.size, currentQuestion = currentQuestion, onNext = { correct ->
        if (correct) {
            counter++
        }

        if (gameSet.isEmpty()) {
            onFinish(counter)
            return@GameQuizGameScreenContent
        }

        currentQuestion = gameSet.removeLast()

    }, onBackPress = onBackPress)
}

@Composable
fun GameQuizGameScreenContent(counter: Int, questionNumber: Int, currentQuestion: QuizObject, onNext: (Boolean) -> Unit, onBackPress: () -> Unit) {

    val starPuls = remember { mutableStateOf(false) }

    var showDialog by remember { mutableStateOf(false) }
    var mistakeString by remember { mutableStateOf("") }

//    val infiniteTransition = rememberInfiniteTransition()
//
//    val scale by infiniteTransition.animateFloat(
//        initialValue = -5f,
//        targetValue = -800f,
//        animationSpec = infiniteRepeatable(
//            animation = tween(50_000),
//            repeatMode = RepeatMode.Reverse
//        )
//    )

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
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .padding(bottom = 3.dp),
                        fontSize = 24.sp,
                        fontFamily = Nunito,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            if (showDialog) {
                // mistake dialog
                Box (
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(top = 200.dp),
                    contentAlignment = Alignment.Center

                ) {
                    Column(
                        modifier = Modifier
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(size = 10.dp)
                            )
                            .padding(horizontal = 20.dp)
                            .padding(top = 20.dp)
                            .padding(bottom = 24.dp)
                            .alpha(0.9f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.oooops),
                            color = QuizMistakeButton,
                            textAlign = TextAlign.Center,
                            fontFamily = Inter,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp
                        )

                        Text(
                            text = mistakeString,
                            color = IconsDark,
                            textAlign = TextAlign.Center,
                            fontFamily = Inter,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .padding(horizontal = 10.dp)
                        )

                        Button(onClick = {
                            showDialog = false
                            onNext(false)
                        },
                            colors = ButtonDefaults.buttonColors(backgroundColor = QuizMistakeButton),
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier
                                .padding(top = 20.dp)) {
                            Text(
                                text = stringResource(id = R.string.got_it),
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                fontFamily = Inter,
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(
                                    horizontal = 10.dp,
                                    vertical = 5.dp
                                )
                            )
                        }
                    }
                }
            } else {
                // question dialog
                Box (
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(top = 160.dp)
                ) {
                    Column (
                        modifier = Modifier
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(size = 10.dp)
                            )
                            .padding(all = 14.dp)
                            .alpha(0.9f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            buildAnnotatedString {
                                withStyle(style = ParagraphStyle(lineHeight = 30.sp)) {
                                    withStyle(style = SpanStyle(
                                        color = MainOrange,
                                        fontFamily = Inter,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 18.sp)
                                    ) {
                                        append("$questionNumber")
                                    }

                                    withStyle(
                                        style = SpanStyle(
                                            color = IconsDark,
                                            fontFamily = Inter,
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 18.sp
                                        )
                                    ) {
                                        append("/${GameUtils.QUIZ_AMOUNT}")
                                    }
                                }
                            }

                        )

                        Text(
                            text = currentQuestion.question,
                            color = IconsDark,
                            textAlign = TextAlign.Center,
                            fontFamily = Inter,
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .padding(horizontal = 5.dp)
                        )

                        val coroutineScope = rememberCoroutineScope()

                        LazyColumn(modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .padding(vertical = 20.dp)
                        ) {

                            items(currentQuestion.answers) {
                                val stateA = remember { mutableStateOf(false) }

                                AnswerButton(text = it.answer, state = stateA.value, isCorrect = it.isCorrect, onClick = {

                                    stateA.value = true
                                    mistakeString = it.explanation

                                    coroutineScope.launch {
                                        delay(500)
                                        stateA.value = false

                                        if (it.isCorrect) {
                                            increaseStars()
                                            onNext(true)
                                        } else {
                                            showDialog = true
                                        }
                                    }


                                })
                            }

                        }

                        Spacer(modifier = Modifier.height(5.dp))

                    }
                }
            }




        }
    }
}

@Composable
fun AnswerButton(text: String, state: Boolean, isCorrect: Boolean, onClick: () -> Unit) {

    Button(
        onClick = {
            onClick()
        },
        colors = ButtonDefaults.buttonColors(backgroundColor = if (state) if (isCorrect) QuizCorrectButton else QuizMistakeButton else Color.White),
        shape = RoundedCornerShape(6.dp),
        elevation = ButtonDefaults.elevation(defaultElevation = 0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .border(
                width = 1.dp,
                color = if (state) Color(0xFFFFFFFF) else Color(0xFFDDDDDD),
                shape = RoundedCornerShape(6.dp)
            )
//            .shadow(elevation = 1.dp, clip = true, shape = RoundedCornerShape(6.dp), spotColor = Color(0xFFDDDDDD), ambientColor = Color(0xFFDDDDDD))
            .defaultMinSize(minHeight = 64.dp)
//            .padding(all = 3.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = text,
                color = if (!state) IconsDark else Color.White,
                fontFamily = Inter,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(horizontal = 5.dp)
            )

            if (state) {
                Image(
                    painter = painterResource(id = if (isCorrect) R.drawable.ic_quiz_correct else R.drawable.ic_quiz_mistake),
                    contentDescription = "Quiz status",
                    contentScale = ContentScale.None,
                    modifier = Modifier.size(20.dp)
                )
            }
        }



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


    GameQuizGameScreenContent(counter = 0, questionNumber = 1, currentQuestion =  QuizObject(
        question = "Which category should broken glass be sorted into?",
        answers = arrayListOf(
            AnswerObject(
                answer = "a) Recycle",
                explanation = "Broken glass cannot be recycled due to safety hazards, contamination risks, and sorting challenges. Please dispose of broken glass in the regular trash to ensure safety and maintain recycling quality.",
                isCorrect = false
            ),
            AnswerObject(
                answer = "b) Compost",
                explanation = "Glass is not a biodegradable material. Glass does not break down naturally and remains intact in the composting process. It is recommended to recycle glass jars instead to conserve resources and reduce waste.",
                isCorrect = false
            ),
            AnswerObject(
                answer = "c) Garbage",
                explanation = "",
                isCorrect = true
            )
        )
    ), onNext = { }, onBackPress = {

    })
}