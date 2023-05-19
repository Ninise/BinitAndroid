package com.ndteam.wasteandroidapp.utils

import android.util.Log
import androidx.annotation.StringRes
import com.ndteam.wasteandroidapp.App
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlin.random.Random

object Utils {

    fun string(res: Int) : String {
        return App.context.getString(res)
    }

    fun log(str: String) {
        Log.d("BEAVER", str)
    }

    fun getRandomFloatInRange(min: Float, max: Float): Float {
        return Random.nextFloat() * (max - min) + min
    }

}