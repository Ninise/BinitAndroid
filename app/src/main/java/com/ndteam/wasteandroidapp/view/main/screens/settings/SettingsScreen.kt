package com.ndteam.wasteandroidapp.view.main.screens.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
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

@Composable
fun SettingsScreen() {
    SettingsScreenContent()
}

@Composable
fun SettingsScreenContent() {
    Column(
        modifier = Modifier
            .background(color = Color.White)
            .padding(horizontal = 20.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_binit_logo),
            contentDescription = "App logo",
            modifier = Modifier.size(90.dp, 50.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        SettingsMenuItem(
            text = stringResource(R.string.invite_friends),
            onClick = {

            }
        )

        SettingsMenuItem(
            text = stringResource(R.string.feedback),
            onClick = {

            }
        )

        SettingsMenuItem(
            text = stringResource(R.string.contact_us),
            onClick = {

            }
        )

        SettingsMenuItem(
            text = stringResource(R.string.about_us),
            onClick = {

            }
        )

        SettingsMenuItem(
            text = stringResource(R.string.rate_the_app),
            onClick = {

            }
        )

        SettingsMenuItem(
            text = stringResource(R.string.report_a_problem),
            onClick = {

            }
        )
    }
}

@Composable
fun SettingsMenuItem(text: String, onClick: () -> Unit) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp)
                .clickable { onClick() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                fontSize = 16.sp,
                fontFamily = Inter,
                fontWeight = FontWeight.Normal,
                color = IconsDark
            )

            Spacer(
                modifier = Modifier.weight(1f)
            )

            Image(
                painter = painterResource(id = R.drawable.ic_arrow_menu),
                contentDescription = "Arrow menu",
                modifier = Modifier.size(20.dp, 20.dp)
            )
        }

        Divider(
            color = DividerColor
        )
    }
}

@Preview
@Composable
fun SettingsScreen_Preview() {
    SettingsScreenContent()
}