package com.ndteam.wasteandroidapp.view.main.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ndteam.wasteandroidapp.models.RecycleType
import com.ndteam.wasteandroidapp.view.main.MainViewModel
import com.ndteam.wasteandroidapp.view.main.screens.main.MainScreen
import com.ndteam.wasteandroidapp.view.main.screens.search.GarbageTypeDetailsScreen
import com.ndteam.wasteandroidapp.view.main.screens.search.SearchMainScreen

@Composable
fun MainNavigation() {

    val GARBAGE_TYPE = "garbage_type"
    val viewModel = hiltViewModel<MainViewModel>()

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = MainScreens.MainScreen.route) {

        composable(route = MainScreens.SearchMainScreen.route) {
            SearchMainScreen(navController = navController)
        }

        composable(
            route = MainScreens.GarbageDetailsScreen.route + "/{$GARBAGE_TYPE}",
            arguments = listOf(
                navArgument(GARBAGE_TYPE) {
                    type = NavType.StringType
                    defaultValue = RecycleType.RECYCLE.name
                    nullable = false
                }
            )
        ) { entry ->
            entry.arguments?.getString(GARBAGE_TYPE)?.let { type ->
                GarbageTypeDetailsScreen(navController = navController, viewModel, RecycleType.parseValue(type))
            }

        }

        composable(route = MainScreens.MainScreen.route) {
            MainScreen(navController = navController, viewModel)
        }

    }
}