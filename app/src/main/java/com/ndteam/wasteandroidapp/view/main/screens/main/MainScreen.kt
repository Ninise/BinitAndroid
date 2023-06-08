package com.ndteam.wasteandroidapp.view.main.screens.main

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ndteam.wasteandroidapp.R
import com.ndteam.wasteandroidapp.ui.theme.BodyText
import com.ndteam.wasteandroidapp.ui.theme.Inter
import com.ndteam.wasteandroidapp.ui.theme.MainOrange
import com.ndteam.wasteandroidapp.ui.theme.SubTitleText
import com.ndteam.wasteandroidapp.view.game.GamePickerActivity
import com.ndteam.wasteandroidapp.view.main.MainViewModel
import com.ndteam.wasteandroidapp.view.main.navigation.MainNavigation

sealed class BottomNavItem(var title:String, var icon:Int, var screen_route:String) {

    object Home : BottomNavItem("Home", R.drawable.ic_main_nav_home,"home")
    object Schedule: BottomNavItem("Schedule",R.drawable.ic_main_nav_schedule,"schedule")
    object Locations: BottomNavItem("Locations",R.drawable.ic_main_nav_locations,"locations")
    object Settings: BottomNavItem("Settings",R.drawable.ic_main_nav_settings,"settings")

}

@Composable
fun HomeScreen() {
    val viewModel = hiltViewModel<MainViewModel>()
    viewModel.downloadData()


    val activity = LocalContext.current as Activity

    MainNavigation(viewModel = viewModel, {
        GamePickerActivity.startActivity(activity)
    })
}

@Composable
fun ScheduleScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Schedule Screen",
            fontWeight = FontWeight.Bold,
            color = MainOrange,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
    }
}

@Composable
fun LocationsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Locations Screen",
            fontWeight = FontWeight.Bold,
            color = MainOrange,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
    }
}

@Composable
fun SettingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Settings Screen",
            fontWeight = FontWeight.Bold,
            color = MainOrange,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
    }
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = BottomNavItem.Home.screen_route) {
        composable(BottomNavItem.Home.screen_route) {
            HomeScreen()
        }
        composable(BottomNavItem.Schedule.screen_route) {
            ScheduleScreen()
        }
        composable(BottomNavItem.Locations.screen_route) {
            LocationsScreen()
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
        BottomNavItem.Schedule,
        BottomNavItem.Locations,
        BottomNavItem.Settings
    )


    androidx.compose.material.BottomNavigation(
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
                        color = if (currentRoute == item.screen_route) MainOrange else BodyText,
                        fontFamily = Inter,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp
                    )
                        },
                selectedContentColor = MainOrange,
                unselectedContentColor = SubTitleText,
                alwaysShowLabel = true,
                selected = currentRoute == item.screen_route,
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
fun MainScreen() {
    MainScreenContent()
}

@Composable
fun MainScreenContent() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation(navController = navController)
        },
    backgroundColor = Color.White,
    contentColor = Color.White,
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                NavigationGraph(navController = navController)
            }
        }
    )
}

@Preview
@Composable
fun MainScreen_Preview() {
    MainScreenContent()
}