package com.whalescale.binit.view.main.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.whalescale.binit.models.RecycleType
import com.whalescale.binit.utils.Const
import com.whalescale.binit.utils.Utils
import com.whalescale.binit.view.main.MainViewModel
import com.whalescale.binit.view.main.screens.article.ArticleDetailsScreen
import com.whalescale.binit.view.main.screens.home.HomeScreen
import com.whalescale.binit.view.main.screens.search.GarbageTypeDetailsScreen
import com.whalescale.binit.view.main.screens.search.SearchMainScreen


const val GARBAGE_TYPE = "garbage_type"
const val SEARCH_QUERY = "search_query"
const val ARTICLE_ID = "article_id"

@Composable
fun MainNavigation(viewModel: MainViewModel, openGame: () -> Unit, search: (String) -> Unit, navController: NavHostController = rememberNavController()) {

    NavHost(navController = navController, startDestination = MainScreens.MainScreen.route) {

        composable(
            route = MainScreens.GameMainScreen.route,
            arguments = listOf()
        ) { _ ->


            LaunchedEffect(Unit, block = {
                navController.popBackStack()
                openGame()
            })

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
                    SearchMainScreen(viewModel)
                } else {
                    SearchMainScreen(viewModel, query)
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
                val category = viewModel.getGarbageCategoryByType(type = type)
                GarbageTypeDetailsScreen(navController = navController, viewModel, category, search = {
                    search(it)
                })
            }

        }

        composable(
            route = MainScreens.ArticleDetailsScreen.route + "/{$ARTICLE_ID}",
            arguments = listOf(
                navArgument(GARBAGE_TYPE) {
                    type = NavType.StringType
                    defaultValue = "0"
                    nullable = false
                }
            )
        ) { entry ->
            entry.arguments?.getString(ARTICLE_ID)?.let { id ->
                val article = viewModel.getArticleById(id = id.toInt())
                ArticleDetailsScreen(navController, viewModel, article)
            }

        }

        composable(route = MainScreens.MainScreen.route) {
            HomeScreen(viewModel, navigation = {
                if (it.startsWith(MainScreens.SearchMainScreen.route)) {
                    search(it.substringAfter("/"))
                } else {
                    navController.navigate(it)
                }
            })
        }

    }
}