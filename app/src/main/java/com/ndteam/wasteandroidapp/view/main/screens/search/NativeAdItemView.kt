package com.ndteam.wasteandroidapp.view.main.screens.search

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import com.appnexus.opensdk.AdView
import com.appnexus.opensdk.NativeAdEventListener
import com.appnexus.opensdk.NativeAdRequest
import com.appnexus.opensdk.NativeAdResponse
import com.appnexus.opensdk.NativeAdSDK
import com.google.ads.AdSize
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.nativead.NativeAd
import com.ndteam.wasteandroidapp.App
import com.ndteam.wasteandroidapp.ui.theme.*
import com.ndteam.wasteandroidapp.utils.Utils


@Composable
fun NativeAdItemView(ad: NativeAd? = null) {

    val composeView = ComposeView(LocalContext.current)
    val composeViewClickThrough: ComposeView = ComposeView(LocalContext.current)

    val nativeAdRequest: NativeAdRequest by lazy {
        NativeAdRequest(App.context, "ca-app-pub-3940256099942544/2247696110")
    }

    AndroidView(factory = {
        composeView.apply {
            setContent {

                Box {
                    Column (modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White)
                        .padding(vertical = 10.dp)) {
                        Row (modifier = Modifier.padding(vertical = 5.dp)) {
                            AsyncImage(
                                model = ad?.images?.first()?.uri,
                                contentDescription = "Ads logo",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(50.dp, height = 50.dp)
                                    .background(color = Color.Black)

                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Column (horizontalAlignment = Alignment.Start) {
                                ad?.headline?.let {
                                    Text(
                                        text = it,
                                        color = TitleText,
                                        fontFamily = OpenSans,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 16.sp,
                                        letterSpacing = 1.sp

                                    )
                                }

                                ClickableText(
                                    text = AnnotatedString("Open"),
                                    style = TextStyle(
                                        color = Color.Blue,
                                        fontFamily = FontFamily.SansSerif,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 14.sp,
                                        letterSpacing = 1.sp
                                    ),
                                    onClick = {

                                    })
                            }

                        }

                        ad?.body?.let {
                            Text(
                                text = it,
                                color = TitleText,
                                fontFamily = OpenSans,
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp,
                                letterSpacing = 1.sp,
                                modifier = Modifier.padding(bottom = 2.dp)
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .width(30.dp)
                            .height(30.dp)
                            .padding(4.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White)
                            .align(alignment = Alignment.TopEnd)
                            .border(
                                1.dp,
                                Color.Gray,
                                shape = RoundedCornerShape(8.dp),
                            )


                    ) {
                        Text(
                            text = "Ad",
                            color = TitleText,
                            fontFamily = OpenSans,
                            fontWeight = FontWeight.Medium,
                            fontSize = 10.sp,
                            letterSpacing = 1.sp,
                            modifier = Modifier.align(alignment = Alignment.Center)
                        )
                    }

                    AndroidView(factory = {
                        composeViewClickThrough.apply { }
                    })

                }
            }

            com.google.android.gms.ads.AdView(App.context).apply {
                setAdSize(com.google.android.gms.ads.AdSize.BANNER)
                adUnitId = "ca-app-pub-3940256099942544/2247696110"
                loadAd(AdRequest.Builder().build())
                setOnClickListener {
                    Utils.log("CLICK AD")
                    composeViewClickThrough.performClick()
                }
            }

        }
    })

    NativeAdSDK.unRegisterTracking(composeView)
    NativeAdSDK.registerTracking(
        null,
        composeView,
        mutableListOf(composeViewClickThrough) as List<View>?,
        object : NativeAdEventListener {
            override fun onAdWasClicked() {
                TODO("Not yet implemented")
            }

            override fun onAdWasClicked(p0: String?, p1: String?) {
                TODO("Not yet implemented")
            }

            override fun onAdWillLeaveApplication() {
                TODO("Not yet implemented")
            }

            override fun onAdImpression() {
                TODO("Not yet implemented")
            }

            override fun onAdAboutToExpire() {
                TODO("Not yet implemented")
            }

            override fun onAdExpired() {
                TODO("Not yet implemented")
            }

        }
    )

}

@Preview
@Composable
fun NativeAdPreview() {
    NativeAdItemView()
}