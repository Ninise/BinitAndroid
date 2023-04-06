package com.ndteam.wasteandroidapp.view.landing

import android.content.Context
import android.hardware.*
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ndteam.wasteandroidapp.api.WasteApi
import com.ndteam.wasteandroidapp.base.BaseActivity
import com.ndteam.wasteandroidapp.models.GarbageItem
import com.ndteam.wasteandroidapp.models.RecycleType
import com.ndteam.wasteandroidapp.ui.theme.WasteAndroidAppTheme
import com.ndteam.wasteandroidapp.utils.NavigationUtils
import com.ndteam.wasteandroidapp.utils.Utils
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
                LandingNavigation {activity ->
                    NavigationUtils.navigate(this, activity)
                    finish()
                }
            }
        }


    }

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    WasteAndroidAppTheme {
//        LandingMainText()
    }
}