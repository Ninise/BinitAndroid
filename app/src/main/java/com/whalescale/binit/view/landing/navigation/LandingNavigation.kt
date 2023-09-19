package com.whalescale.binit.view.landing.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.whalescale.binit.view.landing.screens.LandingMainScreen
import com.whalescale.binit.view.main.MainActivity

@Composable
fun LandingNavigation(navigateToActivity: (Class<out Activity>) -> Unit) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = LandingScreens.LandingLandingScreens.route) {
        composable(route = LandingScreens.LandingLandingScreens.route) {
            LandingMainScreen {
                navigateToActivity(MainActivity::class.java)
            }
        }

//        composable(route = LandingScreens.WelcomeLandingScreens.route) {
//            WelcomeScreen(navController = navController, navigateToActivity)
//        }
    }
}