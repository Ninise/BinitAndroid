package com.ndteam.wasteandroidapp.view.landing

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.ndteam.wasteandroidapp.base.BaseActivity
import com.ndteam.wasteandroidapp.ui.theme.WasteAndroidAppTheme
import dagger.hilt.android.AndroidEntryPoint

class LandingActivity : BaseActivity() {

    private val landingViewModel: LandingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        landingViewModel.downloadData()

        setContent {
            WasteAndroidAppTheme {
                MainText()
            }
        }
    }
}

@Composable
fun MainText() {
    Box(modifier =
    Modifier
        .fillMaxSize()
        .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Waste Android App",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    WasteAndroidAppTheme {
        MainText()
    }
}