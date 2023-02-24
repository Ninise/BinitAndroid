package com.ndteam.wasteandroidapp.view.landing.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ndteam.wasteandroidapp.view.landing.screens.LandingMainScreen
import com.ndteam.wasteandroidapp.view.landing.screens.WelcomeScreen

@Composable
fun LandingNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = LandingScreens.LandingLandingScreens.route) {
        composable(route = LandingScreens.LandingLandingScreens.route) {
            LandingMainScreen(navController = navController)
        }

        composable(route = LandingScreens.WelcomeLandingScreens.route) {
            WelcomeScreen(navController = navController)
        }
    }
}