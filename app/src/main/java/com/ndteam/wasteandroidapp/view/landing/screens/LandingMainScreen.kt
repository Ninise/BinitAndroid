package com.ndteam.wasteandroidapp.view.landing.screens

import android.os.Handler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ndteam.wasteandroidapp.view.landing.navigation.LandingScreens


@Composable
fun LandingMainScreen(navController: NavController) {
    Box(modifier =
    Modifier
        .fillMaxSize()
        .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Waste Android App",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }

    Handler().postDelayed({
        navController.navigate(LandingScreens.WelcomeLandingScreens.route)
    }, 5_00)

}

@Composable
@Preview
fun PreviewLanding() {
    LandingMainScreen(navController = NavController(LocalContext.current))
}