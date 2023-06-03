package com.ndteam.wasteandroidapp.utils

import android.app.Activity
import android.content.Intent

object NavigationUtils {

    const val NAVIGATE_BACK = "NAVIGATE_BACK"

    const val NAVIGATE_TO_DND = "NAVIGATE_TO_DND"
    const val NAVIGATE_TO_QUIZ = "NAVIGATE_TO_QUIZ"

    fun navigate(activity: Activity, toActivity: Class<out Activity>) {
        val intent = Intent(activity, toActivity)
        activity.startActivity(intent)
    }

}