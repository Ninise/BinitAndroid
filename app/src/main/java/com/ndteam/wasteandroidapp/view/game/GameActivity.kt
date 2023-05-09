package com.ndteam.wasteandroidapp.view.game

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import com.ndteam.wasteandroidapp.base.BaseActivity
import com.ndteam.wasteandroidapp.ui.theme.WasteAndroidAppTheme
import com.ndteam.wasteandroidapp.view.main.screens.game.GameMainScreen

class GameActivity: BaseActivity() {

    companion object {
        fun startActivity(context: Activity) {
            val intent = Intent(context, GameActivity::class.java)
            context.startActivity(intent)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WasteAndroidAppTheme {
                GameMainScreen()
            }
        }

    }

}