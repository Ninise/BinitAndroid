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
import com.ndteam.wasteandroidapp.utils.NavigationUtils
import com.ndteam.wasteandroidapp.view.game.screens.GameGuideScreen
import com.ndteam.wasteandroidapp.view.game.screens.GamePickerScreen
import com.ndteam.wasteandroidapp.view.game.screens.GameQuizGameScreen
import com.ndteam.wasteandroidapp.view.main.screens.game.GameMainScreen

val gameSet = listOf(
    GameObject(
        R.drawable.ic_game_item_meat_org,
        "Meat",
        RecycleType.GARBAGE),
    GameObject(
        R.drawable.ic_game_item_credit_card_garb,
        "Credit card",
        RecycleType.GARBAGE),
    GameObject(
        R.drawable.ic_game_item_cup_garb,
        "Ceramic garb",
        RecycleType.GARBAGE),
    GameObject(
        R.drawable.ic_game_item_shopping_bags_garb,
        "Shopping bags",
        RecycleType.GARBAGE),
    GameObject(
        R.drawable.ic_game_item_pencil_garb,
        "Pencil",
        RecycleType.GARBAGE),
    GameObject(
        R.drawable.ic_game_item_pen_garb,
        "Pen",
        RecycleType.GARBAGE),
    GameObject(
        R.drawable.ic_game_item_clothes_garb,
        "Clothes",
        RecycleType.GARBAGE),
    GameObject(
        R.drawable.ic_game_item_balloon_garb,
        "Balloon",
        RecycleType.GARBAGE),
    GameObject(
        R.drawable.ic_game_item_sponge_garb,
        "Sponge",
        RecycleType.GARBAGE),
    GameObject(
        R.drawable.ic_game_item_reuse_bug_garb,
        "Reuse bag",
        RecycleType.GARBAGE),
    GameObject(
        R.drawable.ic_game_item_receipt_garb,
        "Receipt",
        RecycleType.GARBAGE),
    GameObject(
        R.drawable.ic_game_item_label_garb,
        "Label",
        RecycleType.GARBAGE),
    GameObject(
        R.drawable.ic_game_item_key_,
        "Keys",
        RecycleType.GARBAGE),
    GameObject(
        R.drawable.ic_game_item_egg_org,
        "Egg",
        RecycleType.ORGANIC),
    GameObject(
        R.drawable.ic_game_item_bread_org,
        "Bread",
        RecycleType.ORGANIC),
    GameObject(
        R.drawable.ic_game_item_banana_org,
        "Banana",
        RecycleType.ORGANIC),
    GameObject(
        R.drawable.ic_game_item_lemon_org,
        "Lemon",
        RecycleType.ORGANIC),
    GameObject(
        R.drawable.ic_game_item_kiwi_fruit_org,
        "Kiwi",
        RecycleType.ORGANIC),
    GameObject(
        R.drawable.ic_game_item_vegetable_org,
        "Vegetable",
        RecycleType.ORGANIC),
    GameObject(
        R.drawable.ic_game_item_bagel_org,
        "Bagel",
        RecycleType.ORGANIC),
    GameObject(
        R.drawable.ic_game_item_orange_org,
        "Orange",
        RecycleType.ORGANIC),
    GameObject(
        R.drawable.ic_game_item_meat_on_bone_org,
        "Meat on bone",
        RecycleType.ORGANIC),
    GameObject(
        R.drawable.ic_game_item_lotion_bottle_rec,
        "Lotion bottle",
        RecycleType.RECYCLE),
    GameObject(
        R.drawable.ic_game_item_plastic_cup_rec,
        "Plastic cp",
        RecycleType.RECYCLE),
    GameObject(
        R.drawable.ic_game_item_baby_bottle_rec,
        "Baby bottle",
        RecycleType.RECYCLE),
    GameObject(
        R.drawable.ic_game_item_package_rec,
        "Package unwaxed",
        RecycleType.RECYCLE),
    GameObject(
        R.drawable.ic_game_item_toilet_paper_rec,
        "Toilet paper",
        RecycleType.RECYCLE),
    GameObject(
        R.drawable.ic_game_item_notebook_rec,
        "Notebook",
        RecycleType.RECYCLE),
    GameObject(
        R.drawable.ic_game_item_newspaper_rec,
        "Newspaper",
        RecycleType.RECYCLE),
    GameObject(
        R.drawable.ic_game_item_champain_bottle_rec,
        "Champain",
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
                        LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

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