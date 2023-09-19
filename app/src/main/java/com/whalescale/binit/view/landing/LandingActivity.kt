package com.whalescale.binit.view.landing

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.SideEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.microsoft.clarity.Clarity
import com.microsoft.clarity.ClarityConfig
import com.whalescale.binit.base.BaseActivity
import com.whalescale.binit.ui.theme.WasteAndroidAppTheme
import com.whalescale.binit.ui.theme.gameImageTopEdgeBack
import com.whalescale.binit.utils.NavigationUtils
import com.whalescale.binit.utils.Utils
import com.whalescale.binit.view.landing.navigation.LandingNavigation
import com.whalescale.binit.view.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LandingActivity : BaseActivity() {

    private val landingViewModel: LandingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val config = ClarityConfig("iwrvgqgwxn")
        Clarity.initialize(applicationContext, config)

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

                if (Utils.isListWasAnimated()) {
                    NavigationUtils.navigate(this, MainActivity::class.java)
                    finish()
                } else {
                    LandingNavigation { activity ->
                        NavigationUtils.navigate(this, activity)
                        finish()
                    }
                }
            }
        }
    }
}
