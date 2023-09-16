package com.ndteam.wasteandroidapp.view.main.screens.schedule

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ndteam.wasteandroidapp.R
import com.ndteam.wasteandroidapp.ui.theme.*
import com.ndteam.wasteandroidapp.utils.Const.S_ANDROID
import com.ndteam.wasteandroidapp.utils.Const.S_SCHEDULE_NAME
import com.ndteam.wasteandroidapp.utils.Const.S_SUGGESTION
import com.ndteam.wasteandroidapp.view.custom_views.DefaultButton
import com.ndteam.wasteandroidapp.view.main.MainViewModel

@Composable
fun ScheduleScreen() {
    val viewModel = hiltViewModel<MainViewModel>()

    ScheduleScreenContent(makeSuggestion = { name, type, desc, location ->
        viewModel.makeSuggestion(name, type, desc, location)
    })
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ScheduleScreenContent(makeSuggestion: (String, String, String, String) -> Unit) {

    val text = remember {
        mutableStateOf("")
    }

    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(R.string.title_schedule_screen),
            color = IconsDark,
            fontFamily = Inter,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(top = 15.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.ic_schedule_screen_image),
            contentDescription = "Schedule placeholder",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .padding(top = 30.dp)
                .padding(end = 50.dp)
            )

        Text(
            text = stringResource(R.string.sub_title_schedule_screen),
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
            fontSize = 14.sp,
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

        DefaultButton(text = stringResource(id = R.string.send), pressed = {
            if (text.value.isNotEmpty()) {
                makeSuggestion(S_SCHEDULE_NAME, S_SUGGESTION, text.value, S_ANDROID)
            }
            text.value = ""
            Toast.makeText(context, "Message has sent, thank you! :)", Toast.LENGTH_SHORT).show()
            keyboardController?.hide()
        })

    }
}

@Preview
@Composable
fun ScheduleScreen_Preview() {
    ScheduleScreenContent(makeSuggestion = { _, _, _, _ ->

    })
}