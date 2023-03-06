package com.ndteam.wasteandroidapp.view.main.navigation

sealed class MainScreens(val route: String) {
    object SearchMainScreen : MainScreens("search_screen")

    object GarbageDetailsScreen : MainScreens("garbage_details_screen")

    fun withArgs(vararg args: String) : String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}