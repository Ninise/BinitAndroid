package com.ndteam.wasteandroidapp.view.landing.screens

import android.app.Activity
import android.content.Context
import android.hardware.*
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import com.ndteam.wasteandroidapp.R
import com.ndteam.wasteandroidapp.ui.theme.*
import com.ndteam.wasteandroidapp.view.main.MainActivity
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Preview
@Composable
fun preview() {
    WelcomeScreen(
        navController = NavController(LocalContext.current),
        navigateToActivity = {  }
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun WelcomeScreen(navController: NavController, navigateToActivity: (Class<out Activity>) -> Unit) {

    val state = rememberPagerState()

    val slideBackImage = remember { mutableStateOf(R.drawable.ic_welcome_1_city) }
    val slideFrontImage = remember { mutableStateOf(R.drawable.ic_welcome_1_person) }

    val slideTitle = remember { mutableStateOf( navController.context.getString(R.string.reduce_title)) }
    val slideDesc = remember { mutableStateOf( navController.context.getString(R.string.reduce_desc)) }

    val slideDotColor = remember { mutableStateOf( MainBlue ) }

    val nextButtonText = remember { mutableStateOf(navController.context.getString(R.string.next)) }

    val offsetX = remember { mutableStateOf( 0f ) }
    val offsetY = remember { mutableStateOf( 0f ) }

    setupMotion(LocalContext.current, offsetX, offsetY)

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {

        TextButton(
            onClick = {
                navigateToActivity(MainActivity::class.java)
            },
            modifier = Modifier
                .align(alignment = Alignment.TopEnd)
                .padding(15.dp)
        ) {
            Text(
                text = "Skip",
                color = BodyText,
                style = MaterialTheme.typography.h3,
                letterSpacing = 1.sp
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

                            nextButtonText.value = navController.context.getString(R.string.next)

                        }

                        1 -> {
                            slideBackImage.value = R.drawable.ic_welcome_2_city
                            slideFrontImage.value = R.drawable.ic_welcome_2_person

                            slideTitle.value = navController.context.getString(R.string.reuse_title)
                            slideDesc.value = navController.context.getString(R.string.reuse_desc)

                            slideDotColor.value = MainGreen

                            nextButtonText.value = navController.context.getString(R.string.got_it)

                        }

                        2 -> {
                            slideBackImage.value = R.drawable.ic_welcome_3_city
                            slideFrontImage.value = R.drawable.ic_welcome_3_person

                            slideTitle.value = navController.context.getString(R.string.recycle_title)
                            slideDesc.value = navController.context.getString(R.string.recycle_desc)

                            slideDotColor.value = MainOrange

                            nextButtonText.value = navController.context.getString(R.string.let_get_started)
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
                    pagerImage(back = slideBackImage.value, front = slideFrontImage.value, offsetX = offsetX.value, offsetY = offsetY.value)
                    Spacer(modifier = Modifier.height(30.dp))
                    pagerText(slideTitle.value, slideDesc.value)
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

            val scope = rememberCoroutineScope()

            Button(
                onClick = {
                    scope.launch {
                        state.scrollToPage(if (state.currentPage < state.pageCount) state.currentPage + 1 else state.currentPage )
                    }

                    if (state.currentPage === state.pageCount - 1) {
                        navigateToActivity(MainActivity::class.java)
                    }
                },
                colors = ButtonDefaults.buttonColors(slideDotColor.value),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .height(45.dp)) {

                Text(
                    text = nextButtonText.value,
                    color = Color.White,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(horizontal = 30.dp),
                    letterSpacing = 1.sp
                )

            }
        }
    }
    



}

@Composable
fun pagerImage(@DrawableRes back: Int, @DrawableRes front: Int, offsetX: Float, offsetY: Float) {
    Box {
        Image(
            painter = painterResource(id = back),
            contentDescription = "City background",
            modifier = Modifier
                .height(400.dp)
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .graphicsLayer {
                    translationX = offsetX * 10
                    translationY = offsetY * 10

                },
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
fun pagerText(title: String, body: String) {
    Text(
        text = title,
        color = TitleText,
        fontSize = 24.sp,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.h1,
        letterSpacing = 2.sp
    )

    Spacer(modifier = Modifier.height(10.dp))

    Text(
        text = body,
        color = BodyText,
        fontSize = 14.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(horizontal = 30.dp),
        style = MaterialTheme.typography.body1,
        letterSpacing = 1.sp
    )
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
                Spacer(modifier = Modifier.padding(horizontal = 5.dp))
            }
        }
    }
}

private fun setupMotion(context: Context, offsetX: MutableState<Float>, offsetY: MutableState<Float>) {
    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val mSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)

    val listener = object : SensorEventListener {
        // this method is called when the
        // device's position changes
        override fun onSensorChanged(sensorEvent: SensorEvent) {
            // check if listener is
            // different from null
            offsetX.value = sensorEvent.values[0]
            offsetY.value = sensorEvent.values[1]
        }

        override fun onAccuracyChanged(sensor: Sensor?, i: Int) {}
    }

    mSensor?.also { sensor ->
        sensorManager.registerListener(listener, sensor, 1)
    }
}