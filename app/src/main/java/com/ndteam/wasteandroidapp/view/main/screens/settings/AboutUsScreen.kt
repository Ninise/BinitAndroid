package com.ndteam.wasteandroidapp.view.main.screens.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ndteam.wasteandroidapp.R
import com.ndteam.wasteandroidapp.ui.theme.IconsDark
import com.ndteam.wasteandroidapp.ui.theme.Inter
import com.ndteam.wasteandroidapp.ui.theme.MainOrange

@Composable
fun AboutUsScreen(navBack: () -> Unit) {
    AboutUsScreenContent(navBack)
}


@Composable
fun AboutUsScreenContent(navBack: () -> Unit) {

    val scroll = rememberScrollState()

    Column (modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ToolbarWithTitle(stringResource(id = R.string.about_us)) {
            navBack()
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .verticalScroll(scroll)
        ) {
            Text(
                text = stringResource(R.string.sub_title_about_us_screen),
                color = MainOrange,
                fontFamily = Inter,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(top = 30.dp)
                    .align(alignment = Alignment.Start)
            )

            Text(
                text = stringResource(R.string.sub_about_us_screen),
                color = IconsDark,
                fontFamily = Inter,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                modifier = Modifier
                    .padding(top = 1.dp)
                    .align(alignment = Alignment.Start)
            )

            Image(
                painter = painterResource(id = R.drawable.ic_about_us_image),
                contentDescription = "About us picture",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
            )

            Text(
                text = stringResource(R.string.sub_title_about_us_second_screen),
                color = MainOrange,
                fontFamily = Inter,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                modifier = Modifier
                    .align(alignment = Alignment.Start)
            )

            Text(
                text = stringResource(R.string.sub_about_us_second_screen),
                color = IconsDark,
                fontFamily = Inter,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                modifier = Modifier
                    .padding(top = 1.dp)
                    .align(alignment = Alignment.Start)
            )

            Spacer(modifier = Modifier.height(30.dp))
        }
    }

}

@Preview
@Composable
fun AboutUsScreen_Preview() {
    AboutUsScreenContent {

    }
}