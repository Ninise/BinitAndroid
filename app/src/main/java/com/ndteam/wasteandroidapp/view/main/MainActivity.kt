package com.ndteam.wasteandroidapp.view.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.ads.*
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.ndteam.wasteandroidapp.base.BaseActivity
import com.ndteam.wasteandroidapp.ui.theme.WasteAndroidAppTheme
import com.ndteam.wasteandroidapp.utils.Utils
import com.ndteam.wasteandroidapp.view.main.navigation.MainNavigation
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
                val viewModel = hiltViewModel<MainViewModel>()
                MainNavigation(viewModel = viewModel)
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