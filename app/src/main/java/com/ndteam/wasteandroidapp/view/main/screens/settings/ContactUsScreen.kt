package com.ndteam.wasteandroidapp.view.main.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
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
import com.ndteam.wasteandroidapp.ui.theme.DividerColor
import com.ndteam.wasteandroidapp.ui.theme.IconsDark
import com.ndteam.wasteandroidapp.ui.theme.Inter
import com.ndteam.wasteandroidapp.ui.theme.MainOrange

@Composable
fun ContactUsScreen(navBack: () -> Unit) {
    ContactUsScreenContent {
        navBack()
    }
}

@Composable
fun ContactUsScreenContent(navBack: () -> Unit) {

    val scroll = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ToolbarWithTitle(stringResource(id = R.string.contact_us)) {
            navBack()
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .verticalScroll(scroll)
        ) {
            Text(
                text = stringResource(R.string.sub_title_contact_us_screen),
                color = IconsDark,
                fontFamily = Inter,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(top = 30.dp)
                    .align(alignment = Alignment.Start)
            )

            Text(
                text = stringResource(R.string.sub_contact_us_screen),
                color = IconsDark,
                fontFamily = Inter,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(top = 1.dp)
                    .align(alignment = Alignment.Start)
            )
            
            ContactUsItem(title = stringResource(id = R.string.email), handle = "dummyEmail", onCopy = {
                // todo action in viewModel
            })

            Divider(thickness = 1.dp, color = DividerColor)

            ContactUsItem(title = stringResource(id = R.string.website), handle = "dummyWebsite", onCopy = {
                // todo action in viewModel
            })

            Divider(thickness = 1.dp, color = DividerColor)

            ContactUsItem(title = stringResource(id = R.string.facebook), handle = "dummyFacebook", onCopy = {
                // todo action in viewModel
            })

            Divider(thickness = 1.dp, color = DividerColor)

            ContactUsItem(title = stringResource(id = R.string.linked_in), handle = "dummyLinkedIn", onCopy = {
                // todo action in viewModel
            })

            Divider(thickness = 1.dp, color = DividerColor)

            ContactUsItem(title = stringResource(id = R.string.tik_tok), handle = "dummyTikTok", onCopy = {
                // todo action in viewModel
            })

            Divider(thickness = 1.dp, color = DividerColor)

            ContactUsItem(title = stringResource(id = R.string.instagram), handle = "dummyInstagram", onCopy = {
                // todo action in viewModel
            })

            Divider(thickness = 1.dp, color = DividerColor)

            Text(
                text = stringResource(R.string.precise_location),
                color = IconsDark,
                fontFamily = Inter,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(top = 25.dp)
                    .align(alignment = Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(70.dp))

        }
        
        
    }
}

@Composable
fun ContactUsItem(title: String, handle: String, onCopy: (String) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 20.dp)
    ) {
       Column(
           modifier = Modifier.align(alignment = Alignment.CenterVertically)
       ) {
           Text(
               text = title,
               color = IconsDark,
               fontFamily = Inter,
               fontWeight = FontWeight.Medium,
               fontSize = 16.sp,
               modifier = Modifier
                   .align(alignment = Alignment.Start)
           )

           Text(
               text = handle,
               color = MainOrange,
               fontFamily = Inter,
               fontWeight = FontWeight.Normal,
               fontSize = 14.sp,
               modifier = Modifier
                   .padding(top = 5.dp)
                   .align(alignment = Alignment.Start)
           )
       }
        
        Spacer(
            modifier = Modifier.weight(1f)
        )

        IconButton(onClick = {
            onCopy(handle)
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_copy),
                tint = MainOrange,
                contentDescription = "",
                modifier = Modifier
                    .size(24.dp)
            )
        }
    }
}

@Preview
@Composable
fun ContactUsScreen_Preview() {
    ContactUsScreenContent() {

    }
}