package com.ndteam.wasteandroidapp.utils

import android.util.Log
import androidx.annotation.StringRes
import com.ndteam.wasteandroidapp.App
import dagger.hilt.android.qualifiers.ApplicationContext

object Utils {

    fun log(str: String) {
        Log.d("TAG", str)
    }

}