package com.whalescale.binit.view.landing.navigation

sealed class LandingScreens(val route: String) {
    object LandingLandingScreens : LandingScreens("landing_screen")
    object WelcomeLandingScreens : LandingScreens("welcome_screen")

    fun withArgs(vararg args: String) : String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
