package com.ndteam.wasteandroidapp.utils

import android.app.Activity
import android.content.Intent

object NavigationUtils {

    fun navigate(activity: Activity, toActivity: Class<out Activity>) {
        val intent = Intent(activity, toActivity)
        activity.startActivity(intent)
    }

}