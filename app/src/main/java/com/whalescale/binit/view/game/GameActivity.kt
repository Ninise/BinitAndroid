package com.whalescale.binit.view.game

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.whalescale.binit.base.BaseActivity
import com.whalescale.binit.ui.theme.WasteAndroidAppTheme
import com.whalescale.binit.ui.theme.gameImageTopEdgeBack
import com.whalescale.binit.utils.GameUtils
import com.whalescale.binit.view.game.screens.CongratsScreen
import com.whalescale.binit.view.game.screens.GameGuideScreen
import com.whalescale.binit.view.game.screens.GameQuizGameScreen
import com.whalescale.binit.view.main.screens.game.GameMainScreen
import com.whalescale.binit.R

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
                val isCongrats = remember { mutableStateOf(false) }
                val congratsStars = remember { mutableStateOf(0) }

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
                        } else if (isCongrats.value) {
                            CongratsScreen(congratsStars.value, isQuiz = false, onBackPressed = {
                                onBackPressed()
                            }, onPlayPress = {
                                isCongrats.value = false
                            })
                        } else {
                            GameMainScreen(GameUtils.getBatchOfItems(this), onBackPress = {
                                onBackPressed()
                            }, onFinish = { congrats ->
                                congratsStars.value = congrats
                                isCongrats.value = true
                            })
                        }
                    }

                    QUIZ -> {
                        LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

                        if (isCongrats.value) {
                            CongratsScreen(congratsStars.value, isQuiz = true, onBackPressed = {
                                onBackPressed()
                            }, onPlayPress = {
                                isCongrats.value = false
                            })
                        } else {
                            GameQuizGameScreen(GameUtils.getBatchOfQuestions(this), onBackPress = {
                                onBackPressed()
                            }, onFinish = { congrats ->
                                congratsStars.value = congrats
                                isCongrats.value = true
                            })
                        }
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