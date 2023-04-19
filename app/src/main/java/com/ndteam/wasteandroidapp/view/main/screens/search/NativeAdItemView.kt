package com.ndteam.wasteandroidapp.view.main.screens.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.android.gms.ads.nativead.NativeAd
import com.ndteam.wasteandroidapp.ui.theme.*


@Composable
fun NativeAdItemView(ad: NativeAd? = null) {

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

    }


}

@Preview
@Composable
fun NativeAdPreview() {
    NativeAdItemView()
}