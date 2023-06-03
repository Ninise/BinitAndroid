package com.ndteam.wasteandroidapp.view.game.screens

import android.view.LayoutInflater
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.ndteam.wasteandroidapp.R
import kotlinx.coroutines.launch

@Composable
fun BannerAdView() {

   val scope = rememberCoroutineScope()

    AndroidView(
        factory = { context ->
            val adView = LayoutInflater.from(context).inflate(R.layout.layout_admob_banner, null) as AdView

            scope.launch {
                val adRequest = AdRequest.Builder().build()
                adView.loadAd(adRequest)
            }

            adView
        },
        update = {  },
        modifier = Modifier.fillMaxWidth()
    )
}