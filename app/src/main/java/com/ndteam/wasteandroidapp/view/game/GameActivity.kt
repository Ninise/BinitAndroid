package com.ndteam.wasteandroidapp.view.game

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ndteam.wasteandroidapp.R
import com.ndteam.wasteandroidapp.base.BaseActivity
import com.ndteam.wasteandroidapp.models.GameObject
import com.ndteam.wasteandroidapp.models.RecycleType
import com.ndteam.wasteandroidapp.ui.theme.WasteAndroidAppTheme
import com.ndteam.wasteandroidapp.ui.theme.gameImageTopEdgeBack
import com.ndteam.wasteandroidapp.utils.NavigationUtils
import com.ndteam.wasteandroidapp.view.game.screens.GameGuideScreen
import com.ndteam.wasteandroidapp.view.game.screens.GamePickerScreen
import com.ndteam.wasteandroidapp.view.main.screens.game.GameMainScreen

val gameSet = listOf(
    GameObject(
        R.drawable.ic_game_item_meat,
        "Meat",
        RecycleType.GARBAGE),
    GameObject(
        R.drawable.ic_game_item_egg,
        "Egg",
        RecycleType.ORGANIC),
    GameObject(
        R.drawable.ic_game_item_plastic_cup,
        "Plastic cp",
        RecycleType.RECYCLE),

    )
class GameActivity: BaseActivity() {



    companion object {

        const val GAME_TYPE = "GAME_TYPE"

        const val DRAG_AND_DROP = 1
        const val QUIZ = 2

        fun startActivity(context: Activity, type: Int) {
            val intent = Intent(context, GameActivity::class.java)
            intent.putExtra(GAME_TYPE, type)
            context.startActivity(intent)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WasteAndroidAppTheme {

                val isGuideStage = remember { mutableStateOf(true) }

                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = gameImageTopEdgeBack,
                        darkIcons = false
                    )
                }

                when (intent.getIntExtra(GAME_TYPE, DRAG_AND_DROP)) {
                    DRAG_AND_DROP -> {
                        if (isGuideStage.value) {
                            GameGuideScreen(onBackPressed = {
                                onBackPressed()
                            }, onPlayPress = {
                                isGuideStage.value = false
                            })
                        } else {
                            GameMainScreen(gameSet, onBackPress = {
                                onBackPressed()
                            })
                        }
                    }

                    QUIZ -> {

                    }
                }





            }
        }

    }

    override fun onBackPressed() {
        showBackDialog {
            super.onBackPressed()
        }
    }

    private fun showBackDialog(positive: () -> Unit) {
        val dialog = AlertDialog.Builder(this, R.style.AlertDialogTheme)
            .setTitle(getString(R.string.return_to_menu))
            .setMessage(getString(R.string.end_game_content))
            .setPositiveButton(getString(R.string.yes)) { dialogInterface, i ->
                positive()
            }
            .setNegativeButton(getString(R.string.no)) { dialogInterface, i ->

            }
            .create()

        dialog.show()
    }

}