package com.ndteam.wasteandroidapp.view.main.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ndteam.wasteandroidapp.view.main.screens.search.SearchMainScreen

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = MainScreens.SearchMainScreens.route) {
        composable(route = MainScreens.SearchMainScreens.route) {
            SearchMainScreen(navController = navController)
        }

    }
}