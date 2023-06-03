package com.ndteam.wasteandroidapp.view.landing

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ndteam.wasteandroidapp.base.BaseActivity
import com.ndteam.wasteandroidapp.ui.theme.WasteAndroidAppTheme
import com.ndteam.wasteandroidapp.ui.theme.gameImageTopEdgeBack
import com.ndteam.wasteandroidapp.utils.NavigationUtils
import com.ndteam.wasteandroidapp.view.landing.navigation.LandingNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LandingActivity : BaseActivity() {

    private val landingViewModel: LandingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        landingViewModel.downloadData()

        setContent {
            WasteAndroidAppTheme {

                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = gameImageTopEdgeBack,
                        darkIcons = false
                    )
                }

                LandingNavigation {activity ->
                    NavigationUtils.navigate(this, activity)
                    finish()
                }
            }
        }
    }
}
