package com.ndteam.wasteandroidapp.view.main.screens.drop_locations

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ndteam.wasteandroidapp.R
import com.ndteam.wasteandroidapp.ui.theme.*

@Composable
fun DropOffLocationsScreen() {
    DropOffLocationsScreenContent()
}

@Composable
fun DropOffLocationsScreenContent() {

    val text = remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(R.string.title_drop_location_screen),
            color = IconsDark,
            fontFamily = Inter,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(top = 15.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.ic_location_screen_image),
            contentDescription = "Drop-ff placeholder",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .padding(top = 30.dp)
                .padding(end = 50.dp)
        )

        Text(
            text = stringResource(R.string.sub_title_drop_locations_screen),
            color = IconsDark,
            fontFamily = Inter,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(top = 30.dp)
                .align(alignment = Alignment.Start)
        )

        Text(
            text = stringResource(R.string.sub_schedule_screen),
            color = IconsDark,
            fontFamily = Inter,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(top = 1.dp)
                .align(alignment = Alignment.Start)
        )

        TextField(
            value = text.value,
            onValueChange = { value ->
                text.value = value
            },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 16.dp),
            maxLines = 5,
            minLines = 5,
            placeholder = {
                Text(stringResource(R.string.message), style = TextStyle(color = HintText, fontSize = 16.sp, fontFamily = Inter, fontWeight = FontWeight.Normal))
            },
            textStyle = TextStyle(color = BodyText, fontSize = 16.sp, fontFamily = Inter, fontWeight = FontWeight.Normal),
            singleLine = false,
            shape = RoundedCornerShape(6.dp), // The TextFiled has rounded corners top left and right by default
            colors = TextFieldDefaults.textFieldColors(
                textColor = BodyText,
                cursorColor = cursorText,
                backgroundColor = SearchBackGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )

        Button(
            onClick = {

            },
            colors = ButtonDefaults.buttonColors(backgroundColor = MainOrange),
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)) {

            Text(
                text = stringResource(id = R.string.send),
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

@Preview
@Composable
fun DropOffLocationsScreen_Preview() {
    DropOffLocationsScreenContent()
}