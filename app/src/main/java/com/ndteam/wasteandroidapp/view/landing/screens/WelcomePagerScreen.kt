package com.ndteam.wasteandroidapp.view.landing.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import com.google.android.material.animation.AnimationUtils.lerp
import com.ndteam.wasteandroidapp.R
import com.ndteam.wasteandroidapp.ui.theme.*
import kotlin.math.absoluteValue

@Preview
@Composable
fun preview() {
    WelcomeScreen(navController = NavController(LocalContext.current))
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun WelcomeScreen(navController: NavController) {

    val state = rememberPagerState()

    val slideBackImage = remember { mutableStateOf(R.drawable.ic_welcome_1_city) }
    val slideFrontImage = remember { mutableStateOf(R.drawable.ic_welcome_1_person) }

    val slideTitle = remember { mutableStateOf( navController.context.getString(R.string.reduce_title)) }
    val slideDesc = remember { mutableStateOf( navController.context.getString(R.string.reduce_desc)) }

    val slideDotColor = remember { mutableStateOf( MainBlue ) }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {

        TextButton(
            onClick = {},
            modifier = Modifier
                .align(alignment = Alignment.TopEnd)
                .padding(15.dp)
        ) {
            Text(
                text = "Skip",
                color = BodyText
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 100.dp)) {

            LaunchedEffect(state) {
                snapshotFlow {
                    state.currentPage
                }.collect { page ->
                    when(page)  {
                        0 -> {
                            slideBackImage.value = R.drawable.ic_welcome_1_city
                            slideFrontImage.value = R.drawable.ic_welcome_1_person

                            slideTitle.value = navController.context.getString(R.string.reduce_title)
                            slideDesc.value = navController.context.getString(R.string.reduce_desc)

                            slideDotColor.value = MainBlue

                        }

                        1 -> {
                            slideBackImage.value = R.drawable.ic_welcome_2_city
                            slideFrontImage.value = R.drawable.ic_welcome_2_person

                            slideTitle.value = navController.context.getString(R.string.reuse_title)
                            slideDesc.value = navController.context.getString(R.string.reuse_desc)

                            slideDotColor.value = MainGreen

                        }

                        2 -> {
                            slideBackImage.value = R.drawable.ic_welcome_3_city
                            slideFrontImage.value = R.drawable.ic_welcome_3_person

                            slideTitle.value = navController.context.getString(R.string.recycle_title)
                            slideDesc.value = navController.context.getString(R.string.recycle_desc)

                            slideDotColor.value = MainOrange
                        }
                    }
                }
            }


            HorizontalPager(count = 3, state = state) { page ->

                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .graphicsLayer {
                            // Calculate the absolute offset for the current page from the
                            // scroll position. We use the absolute value which allows us to mirror
                            // any effects for both directions
                            val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                            // We animate the scaleX + scaleY, between 85% and 100%
                            lerp(
                                start = 0.55f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            ).also { scale ->
                                scaleX = scale
                                scaleY = scale
                            }

                            // We animate the alpha, between 50% and 100%
                            alpha = lerp(
                                start = 0.3f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            )
                        }) {
                    pagerImage(back = slideBackImage.value, front = slideFrontImage.value)
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(
                        text = slideTitle.value,
                        color = TitleText,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center
                        )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = slideDesc.value,
                        color = BodyText,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 50.dp)
                        )
                }

            }

            Spacer(modifier = Modifier.height(20.dp))

            DotsIndicator(
                totalDots = 3,
                selectedIndex = state.currentPage,
                selectedColor = slideDotColor.value,
                unSelectedColor = Color.LightGray
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(slideDotColor.value),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .width(130.dp)
                    .height(45.dp)) {
                Text(text = "Next", color = Color.White)
            }
        }
    }
    



}

@Composable
fun pagerImage(@DrawableRes back: Int, @DrawableRes front: Int,) {
    Box {
        Image(
            painter = painterResource(id = back),
            contentDescription = "City background",
            modifier = Modifier
                .height(400.dp)
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            contentScale = ContentScale.Fit
        )

        Image(
            painter = painterResource(id = front),
            contentDescription = "Foreground person",
            modifier = Modifier
                .height(400.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun DotsIndicator(
    totalDots : Int,
    selectedIndex : Int,
    selectedColor : Color,
    unSelectedColor: Color) {

    LazyRow(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()

    ) {

        items(totalDots) { index ->
            if (index == selectedIndex) {
                Box(
                    modifier = Modifier
                        .size(7.dp)
                        .clip(CircleShape)
                        .background(selectedColor)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(7.dp)
                        .clip(CircleShape)
                        .background(unSelectedColor)
                )
            }

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}