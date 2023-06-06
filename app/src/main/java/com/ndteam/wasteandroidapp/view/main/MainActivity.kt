package com.ndteam.wasteandroidapp.view.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ndteam.wasteandroidapp.base.BaseActivity
import com.ndteam.wasteandroidapp.ui.theme.WasteAndroidAppTheme
import com.ndteam.wasteandroidapp.view.game.GamePickerActivity
import com.ndteam.wasteandroidapp.view.main.navigation.MainNavigation
import com.ndteam.wasteandroidapp.view.main.screens.main.MainScreen
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    companion object {
        fun startActivity(context: Activity) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel.downloadData()

        setContent {
            WasteAndroidAppTheme {

                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = Color.White,
                        darkIcons = true
                    )
                }

//                val viewModel = hiltViewModel<MainViewModel>()
//                MainNavigation(viewModel = viewModel, {
//                    GamePickerActivity.startActivity(this)
//                })

                MainScreen()
            }
        }


    }

}