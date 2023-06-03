package com.ndteam.wasteandroidapp.view.landing.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ndteam.wasteandroidapp.R
import com.ndteam.wasteandroidapp.ui.theme.IconsDark
import com.ndteam.wasteandroidapp.ui.theme.Inter
import com.ndteam.wasteandroidapp.ui.theme.MainOrange
import com.ndteam.wasteandroidapp.ui.theme.WhaleScaleColor


@Composable
fun LandingMainScreen(callback: () -> Unit) {

    Box (modifier =
    Modifier
        .fillMaxSize()
        .background(color = Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_game_background),
            contentDescription = "Screen back",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .fillMaxSize()
        )

       Box (modifier = Modifier
           .align(alignment = Alignment.Center)

       ) {
           Column (
               modifier = Modifier
                   .background(color = Color.White, shape = RoundedCornerShape(size = 10.dp))
                   .padding(horizontal = 30.dp)
                   .padding(bottom = 30.dp)
                   .padding(top = 20.dp)
                   .alpha(0.9f),
               horizontalAlignment = Alignment.CenterHorizontally
           ) {
               Image(
                   painter = painterResource(id = R.drawable.ic_binit_logo),
                   contentDescription = "App logo",
                   contentScale = ContentScale.Fit,
                   modifier = Modifier
                       .width(100.dp)

               )

               Text(
                   text = stringResource(R.string.landing_dialog_title),
                   color = IconsDark,
                   textAlign = TextAlign.Center,
                   fontFamily = Inter,
                   fontWeight = FontWeight.SemiBold,
                   fontSize = 18.sp,
                   modifier = Modifier
                       .padding(top = 5.dp)

               )

               Text(
                   text = stringResource(id = R.string.landing_dialog_content),
                   color = IconsDark,
                   textAlign = TextAlign.Center,
                   fontFamily = Inter,
                   fontWeight = FontWeight.Normal,
                   fontSize = 16.sp,
                   lineHeight = 20.sp,
                   modifier = Modifier.padding(top = 5.dp)
               )

               Button(
                   onClick = {
                        callback()
                   },
                   colors = ButtonDefaults.buttonColors(backgroundColor = MainOrange),
                   shape = RoundedCornerShape(6.dp),
                   modifier = Modifier
                       .padding(top = 20.dp)
                       .height(45.dp)) {

                   Text(
                       text = stringResource(R.string.landing_dialog_button),
                       color = Color.White,
                       fontFamily = Inter,
                       fontWeight = FontWeight.Medium,
                       fontSize = 14.sp,
                       modifier = Modifier.padding(
                           horizontal = 10.dp
                       )
                   )

               }
           }
       }

    }

}

@Composable
@Preview
fun PreviewLanding() {
    LandingMainScreen() {

    }
}