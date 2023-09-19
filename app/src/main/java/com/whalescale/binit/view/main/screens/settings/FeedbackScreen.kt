package com.whalescale.binit.view.main.screens.settings

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whalescale.binit.ui.theme.*
import com.whalescale.binit.utils.Const
import com.whalescale.binit.utils.Utils
import com.whalescale.binit.view.custom_views.DefaultButton
import com.whalescale.binit.view.main.MainViewModel
import com.whalescale.binit.R

@Composable
fun FeedbackScreen(navBack: () -> Unit) {
    val viewModel = hiltViewModel<MainViewModel>()
    
    FeedbackScreenContent(navBack, makeSuggestion = { name, type, desc, location ->
        viewModel.makeSuggestion(name, type, desc, location)
    })
}

@Composable
fun FeedbackScreenContent(navBack: () -> Unit, makeSuggestion: (String, String, String, String) -> Unit) {

    val email = remember { mutableStateOf("") }
    val message = remember { mutableStateOf("") }
    val isEmailCorrect = remember { mutableStateOf(true) }
    val context = LocalContext.current

    Column (modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ToolbarWithTitle(stringResource(id = R.string.feedback)) {
            navBack()
        }

        Column(
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            Text(
                text = stringResource(R.string.sub_title_feedback_screen),
                color = IconsDark,
                fontFamily = Inter,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(top = 30.dp)
                    .align(alignment = Alignment.Start)
            )

            Text(
                text = stringResource(R.string.sub_feedback_screen),
                color = IconsDark,
                fontFamily = Inter,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(top = 1.dp)
                    .align(alignment = Alignment.Start)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Text(
                    text = stringResource(R.string.email),
                    color = IconsDark,
                    fontFamily = Inter,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                )

                Text(
                    text = stringResource(R.string.optional),
                    color = IconsDark,
                    fontFamily = Inter,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 5.dp)
                )

                if (!isEmailCorrect.value) {
                    Text(
                        text = stringResource(R.string.email_isnt_valid),
                        color = QuizMistakeButton,
                        fontFamily = Inter,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }

            }

            TextField(
                value = email.value,
                onValueChange = { value ->
                    email.value = value

                    if (!isEmailCorrect.value) {
                        isEmailCorrect.value = Utils.isValidEmail(email.value)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 10.dp)
                    .border(width = 1.dp, color = if (isEmailCorrect.value) Color.Transparent else QuizMistakeButton, shape = RoundedCornerShape(6.dp)),
                placeholder = {
                    Text(stringResource(R.string.email_placeholder), style = TextStyle(color = HintText, fontSize = 16.sp, fontFamily = Inter, fontWeight = FontWeight.Normal))
                },
                textStyle = TextStyle(color = BodyText, fontSize = 16.sp, fontFamily = Inter, fontWeight = FontWeight.Normal),
                singleLine = true,
                shape = RoundedCornerShape(6.dp),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = BodyText,
                    cursorColor = cursorText,
                    backgroundColor = SearchBackGray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )


            Text(
                text = stringResource(R.string.feedback),
                color = IconsDark,
                fontFamily = Inter,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
            )

            TextField(
                value = message.value,
                onValueChange = { value ->
                    message.value = value
                    isEmailCorrect.value = Utils.isValidEmail(email.value)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 10.dp),
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

            DefaultButton(text = stringResource(id = R.string.send), modifier = Modifier.padding(top = 10.dp), pressed = {
                if (email.value.isEmpty()) {
                    Toast.makeText(context, "Email should be filled.", Toast.LENGTH_SHORT).show()
                    return@DefaultButton
                }

                if (message.value.isEmpty()) {
                    Toast.makeText(context, "Message should be filled.", Toast.LENGTH_SHORT).show()
                    return@DefaultButton
                }

                if (message.value.isNotEmpty()) {
                    makeSuggestion(
                        Const.S_FEEDBACK_NAME,
                        Const.S_FEEDBACK, "EMAIL: ${email.value}; MESSAGE: ${message.value}",
                        Const.S_ANDROID
                    )

                    email.value = ""
                    message.value = ""

                    Toast.makeText(context, "Feedback has been sent. Thank you! :)", Toast.LENGTH_SHORT).show()
                }

            })

        }



    }
}

@Preview
@Composable
fun FeedbackScreen_Preview() {
    FeedbackScreenContent(navBack = {

    }, makeSuggestion = { name, type, desc, location ->

    })
}
