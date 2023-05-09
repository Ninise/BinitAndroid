package com.ndteam.wasteandroidapp.view.main.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ndteam.wasteandroidapp.models.RecycleType
import com.ndteam.wasteandroidapp.utils.Const
import com.ndteam.wasteandroidapp.view.game.GameActivity
import com.ndteam.wasteandroidapp.view.main.MainViewModel
import com.ndteam.wasteandroidapp.view.main.screens.game.GameMainScreen
import com.ndteam.wasteandroidapp.view.main.screens.main.MainScreen
import com.ndteam.wasteandroidapp.view.main.screens.search.GarbageTypeDetailsScreen
import com.ndteam.wasteandroidapp.view.main.screens.search.SearchMainScreen

@Composable
fun MainNavigation(viewModel: MainViewModel, openGame: () -> Unit, navController: NavHostController = rememberNavController()) {

    val GARBAGE_TYPE = "garbage_type"
    val SEARCH_QUERY = "search_query"

    NavHost(navController = navController, startDestination = MainScreens.MainScreen.route) {

        composable(
            route = MainScreens.GameMainScreen.route,
            arguments = listOf()
        ) { _ ->
            openGame()
        }

        composable(
            route = MainScreens.SearchMainScreen.route + "/{$SEARCH_QUERY}",
            arguments = listOf(
                navArgument(SEARCH_QUERY) {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                }
            )
        ) { entry ->
            entry.arguments?.getString(SEARCH_QUERY, "")?.let { query ->

                if (query == Const.SEARCH_QUERY_DEFAULT) {
                    SearchMainScreen(navController = navController, viewModel)
                } else {
                    SearchMainScreen(navController = navController, viewModel, query)
                }


            }
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
                val category = viewModel.getGarbageCategoryByType(type = RecycleType.parseValue(type))
                GarbageTypeDetailsScreen(navController = navController, viewModel, category)
            }

        }

        composable(route = MainScreens.MainScreen.route) {
            MainScreen(navController = navController, viewModel)
        }

    }
}