package com.ndteam.wasteandroidapp.view.game.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ndteam.wasteandroidapp.R
import com.ndteam.wasteandroidapp.ui.theme.IconsDark
import com.ndteam.wasteandroidapp.ui.theme.Inter
import com.ndteam.wasteandroidapp.ui.theme.MainOrange
import com.ndteam.wasteandroidapp.utils.GameUtils

@Composable
fun CongratsScreen(stars: Int, isQuiz: Boolean, onPlayPress: () -> Unit, onBackPressed: () -> Unit) {

    CongratsScreenContent(stars = stars, isQuiz = isQuiz, onPlayPress = onPlayPress, onBackPressed = onBackPressed)

}

@Composable
fun CongratsScreenContent(stars: Int, isQuiz: Boolean, onPlayPress: () -> Unit, onBackPressed: () -> Unit) {
    Box (contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.ic_game_background),
            contentDescription = "Game background",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Row(
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .padding(top = 10.dp, start = 25.dp, end = 25.dp)
        ) {

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

            Spacer(
                modifier = Modifier
                    .weight(1f)
                    .background(color = Color.Magenta)
            )



        }

        GameOverDialog(
            stars = stars,
            isQuiz = isQuiz,
            title = GameUtils.getCongratsTextBasedOnScore(stars),
            sub = GameUtils.getCongratsSubsTextBasedOnScore(stars),
            onPlayPress = onPlayPress
        )

    }

}


@Composable
fun GameOverDialog(stars: Int, isQuiz: Boolean, title: String, sub: String, onPlayPress: () -> Unit) {
    Box {
        Column(
            modifier = Modifier.align(alignment = Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                modifier = Modifier
                    .background(color = Color.White, shape = RoundedCornerShape(size = 10.dp))
                    .padding(top = 8.dp)
                    .padding(bottom = 24.dp)
                    .padding(horizontal = 20.dp)
                    .alpha(0.9f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                if (!isQuiz) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_congrats_stars_dnd),
                        contentDescription = "Quiz status",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .width(170.dp)
                            .height(80.dp)
                    )
                }

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    buildAnnotatedString {
                        withStyle(style = ParagraphStyle(lineHeight = 30.sp)) {
                            withStyle(
                                style = SpanStyle(
                                    color = MainOrange,
                                    fontFamily = Inter,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                            ) {
                                append("${stars}")
                            }

                            withStyle(
                                style = SpanStyle(
                                    color = MainOrange,
                                    fontFamily = Inter,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                            ) {
                                append("/${if (isQuiz) GameUtils.QUIZ_AMOUNT else GameUtils.DND_ITEMS_AMOUNT}")
                            }
                        }
                    }
                )

                if (isQuiz) {
                    LazyRow (modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)) {
                        items(stars) {
                            Spacer(modifier = Modifier.width(5.dp))
                            Image(
                                painter = painterResource(id = R.drawable.ic_game_star),
                                contentDescription = "DND status",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                        }
                    }
                }

                Text(
                    text = title,
                    color = IconsDark,
                    textAlign = TextAlign.Center,
                    fontFamily = Inter,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(top = 10.dp)
                )

                Text(
                    text = sub,
                    color = IconsDark,
                    textAlign = TextAlign.Center,
                    fontFamily = Inter,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(top = 6.dp)
                )

                Button(
                    onClick = {
                        onPlayPress()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MainOrange),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier
                        .padding(top = 16.dp, start = 10.dp, end = 10.dp)
                ) {

                    Text(
                        text = stringResource(R.string.play_again),
                        color = Color.White,
                        fontFamily = Inter,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(
                            horizontal = 10.dp,
                            vertical = 5.dp
                        )
                    )

                }
            }

        }
    }
}

@Preview(device = Devices.AUTOMOTIVE_1024p, widthDp = 1024, heightDp = 720)
@Composable
fun CongratsScreenContent_Preview() {
    CongratsScreenContent(stars = 4, isQuiz = true, onBackPressed = {}, onPlayPress = {})
}