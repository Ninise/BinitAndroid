package com.ndteam.wasteandroidapp.view.game

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ndteam.wasteandroidapp.base.BaseActivity
import com.ndteam.wasteandroidapp.ui.theme.WasteAndroidAppTheme
import com.ndteam.wasteandroidapp.ui.theme.gameImageTopEdgeBack
import com.ndteam.wasteandroidapp.utils.NavigationUtils
import com.ndteam.wasteandroidapp.utils.Utils
import com.ndteam.wasteandroidapp.view.game.screens.GamePickerScreen

class GamePickerActivity: BaseActivity() {



    companion object {
        fun startActivity(context: Activity) {
            val intent = Intent(context, GamePickerActivity::class.java)
            context.startActivity(intent)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            WasteAndroidAppTheme {

                val destination = remember { mutableStateOf("") }

                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = Color.White,
                        darkIcons = true
                    )
                }

                GamePickerScreen(navigate = {
                    destination.value = it
                })

                when (destination.value) {

                    // Navigate to the main screen
                    NavigationUtils.NAVIGATE_BACK -> {
                        super.onBackPressed()
                    }

                    // Go next to quiz game
                    NavigationUtils.NAVIGATE_TO_QUIZ -> {
                        GameActivity.startActivity(this, GameActivity.QUIZ)
                    }

                    // Go next to Drag and Drop game
                    NavigationUtils.NAVIGATE_TO_DND -> {
                        GameActivity.startActivity(this, GameActivity.DRAG_AND_DROP)
                    }

                    else -> {
                        Utils.log("WRONG NAVIGATION")
                    }
                }

            }
        }

    }
}