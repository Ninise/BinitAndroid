package com.ndteam.wasteandroidapp.view.game

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ndteam.wasteandroidapp.R
import com.ndteam.wasteandroidapp.base.BaseActivity
import com.ndteam.wasteandroidapp.models.GameObject
import com.ndteam.wasteandroidapp.models.RecycleType
import com.ndteam.wasteandroidapp.ui.theme.WasteAndroidAppTheme
import com.ndteam.wasteandroidapp.ui.theme.gameImageTopEdgeBack
import com.ndteam.wasteandroidapp.utils.GameUtils
import com.ndteam.wasteandroidapp.utils.NavigationUtils
import com.ndteam.wasteandroidapp.view.game.screens.CongratsScreen
import com.ndteam.wasteandroidapp.view.game.screens.GameGuideScreen
import com.ndteam.wasteandroidapp.view.game.screens.GamePickerScreen
import com.ndteam.wasteandroidapp.view.game.screens.GameQuizGameScreen
import com.ndteam.wasteandroidapp.view.main.screens.game.GameMainScreen

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
                        LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

                        if (isGuideStage.value) {
                            GameGuideScreen(onBackPressed = {
                                onBackPressed()
                            }, onPlayPress = {
                                isGuideStage.value = false
                            })
                        } else {
                            GameMainScreen(GameUtils.getBatchOfItems(this), onBackPress = {
                                onBackPressed()
                            }, onFinish = { congrats ->
//                                CongratsScreen(congrats, onBackPressed = {
//
//                                }, onPlayPress = {
//
//                                })
                            })
                        }
                    }

                    QUIZ -> {
                        LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)


                        GameQuizGameScreen(onBackPress = {
                            onBackPressed()
                        })
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

@Composable
fun LockScreenOrientation(orientation: Int) {
    val context = LocalContext.current
    DisposableEffect(orientation) {
        val activity = context.findActivity() ?: return@DisposableEffect onDispose {}
        val originalOrientation = activity.requestedOrientation
        activity.requestedOrientation = orientation
        onDispose {
            // restore original orientation when view disappears
            activity.requestedOrientation = originalOrientation
        }
    }
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}