package com.whalescale.binit.view.main.screens.main

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.whalescale.binit.R
import com.whalescale.binit.ui.theme.BodyText
import com.whalescale.binit.ui.theme.Inter
import com.whalescale.binit.ui.theme.MainOrange
import com.whalescale.binit.ui.theme.SubTitleText
import com.whalescale.binit.utils.Const
import com.whalescale.binit.utils.Utils
import com.whalescale.binit.view.game.GamePickerActivity
import com.whalescale.binit.view.main.MainViewModel
import com.whalescale.binit.view.main.navigation.MainNavigation
import com.whalescale.binit.view.main.navigation.MainScreens
import com.whalescale.binit.view.main.navigation.SEARCH_QUERY
import com.whalescale.binit.view.main.screens.drop_locations.DropOffLocationsScreen
import com.whalescale.binit.view.main.screens.schedule.ScheduleScreen
import com.whalescale.binit.view.main.screens.search.SearchMainScreen
import com.whalescale.binit.view.main.screens.search.SearchScreen
import com.whalescale.binit.view.main.screens.settings.SettingsScreen

sealed class BottomNavItem(var title:String, var icon:Int, var screen_route:String) {

    object Home : BottomNavItem("Home", R.drawable.ic_main_nav_home,"home")
    object Search: BottomNavItem("Search",R.drawable.ic_search_mag_glass,"search")
    object Schedule: BottomNavItem("Schedule",R.drawable.ic_main_nav_schedule,"schedule")
    object Settings: BottomNavItem("Settings",R.drawable.ic_main_nav_settings,"settings")

}

@Composable
fun HomeMainScreen(viewModel: MainViewModel, search: (String) -> Unit) {

    val activity = LocalContext.current as Activity

    MainNavigation(viewModel, {
        GamePickerActivity.startActivity(activity)
    }, search = {
        search(it)
    })
}

@Composable
fun NavigationGraph(viewModel: MainViewModel, navController: NavHostController) {
    NavHost(navController, startDestination = BottomNavItem.Home.screen_route) {
        composable(BottomNavItem.Home.screen_route) {
            HomeMainScreen(viewModel, search = {
                navController.navigate("${BottomNavItem.Search.screen_route}/${it}") {
                    navArgument(SEARCH_QUERY) {
                        type = NavType.StringType
                        defaultValue = ""
                        nullable = true
                    }
                    navController.graph.startDestinationRoute?.let { screen_route ->
                        popUpTo(screen_route) {
                            saveState = false
                            inclusive = true
                        }
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            })
        }
        composable( "${BottomNavItem.Search.screen_route}/{$SEARCH_QUERY}") {
            val query = it.arguments?.getString(SEARCH_QUERY, Const.SEARCH_QUERY_DEFAULT) ?: ""

            if (query == Const.SEARCH_QUERY_DEFAULT) {
                SearchScreen()
            } else {
                SearchScreen(query = query)
            }
        }
        composable(BottomNavItem.Search.screen_route) {
            SearchScreen()
        }
        composable(BottomNavItem.Schedule.screen_route) {
            ScheduleScreen()
        }
        composable(BottomNavItem.Settings.screen_route) {
            SettingsScreen()
        }
    }
}

@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Search,
        BottomNavItem.Schedule,
        BottomNavItem.Settings
    )


    BottomNavigation(
        backgroundColor = colorResource(id = R.color.white),
        contentColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                        Icon(painterResource(id = item.icon), contentDescription = item.title)
                       },
                label = {
                    Text(
                        text = item.title,
                        color = if (currentRoute?.startsWith(item.screen_route) == true) MainOrange else BodyText,
                        fontFamily = Inter,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp
                    )
                        },
                selectedContentColor = MainOrange,
                unselectedContentColor = SubTitleText,
                alwaysShowLabel = true,
                selected = currentRoute?.startsWith(item.screen_route) == true,
                onClick = {
                    navController.navigate(item.screen_route) {

                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun MainScreen(viewModel: MainViewModel) {
    MainScreenContent(viewModel)
}

@Composable
fun MainScreenContent(viewModel: MainViewModel) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation(navController = navController)
        },
    backgroundColor = Color.White,
    contentColor = Color.White,
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                NavigationGraph(viewModel = viewModel, navController = navController)
            }
        }
    )
}

@Preview
@Composable
fun MainScreen_Preview() {
    MainScreenContent(viewModel = hiltViewModel())
}