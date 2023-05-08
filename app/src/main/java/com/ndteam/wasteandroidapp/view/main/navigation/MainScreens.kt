package com.ndteam.wasteandroidapp.view.main.navigation

sealed class MainScreens(val route: String) {

    object GameMainScreen : MainScreens("game_screen")

    object SearchMainScreen : MainScreens("search_screen")

    object GarbageDetailsScreen : MainScreens("garbage_details_screen")

    object MainScreen : MainScreens("main_screen")

    fun withArgs(vararg args: String) : String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}