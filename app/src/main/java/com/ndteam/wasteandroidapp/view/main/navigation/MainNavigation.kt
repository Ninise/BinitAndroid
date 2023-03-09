package com.ndteam.wasteandroidapp.view.main.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ndteam.wasteandroidapp.view.main.screens.main.MainScreen
import com.ndteam.wasteandroidapp.view.main.screens.search.GarbageTypeDetailsScreen
import com.ndteam.wasteandroidapp.view.main.screens.search.SearchMainScreen

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = MainScreens.MainScreen.route) {

        composable(route = MainScreens.SearchMainScreen.route) {
            SearchMainScreen(navController = navController)
        }

        composable(route = MainScreens.GarbageDetailsScreen.route) {
            GarbageTypeDetailsScreen(navController = navController)
        }

        composable(route = MainScreens.MainScreen.route) {
            MainScreen(navController = navController)
        }

    }
}