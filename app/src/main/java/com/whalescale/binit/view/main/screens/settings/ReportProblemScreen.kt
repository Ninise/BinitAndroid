package com.whalescale.binit.view.main.screens.settings

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.whalescale.binit.R
import com.whalescale.binit.ui.theme.*
import com.whalescale.binit.utils.Const
import com.whalescale.binit.view.custom_views.DefaultButton
import com.whalescale.binit.view.main.MainViewModel

// decide with image attaching

@Composable
fun ReportProblemScreen(navBack: () -> Unit) {
    val viewModel = hiltViewModel<MainViewModel>()

    ReportProblemScreenContent(navBack, makeSuggestion = { name, type, desc, location ->
        viewModel.makeSuggestion(name, type, desc, location)
    })
}

@Composable
fun ReportProblemScreenContent(navBack: () -> Unit, makeSuggestion: (String, String, String, String) -> Unit) {

    val text = remember { mutableStateOf("") }

    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val context = LocalContext.current

    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uris -> selectedImageUri = uris }
    )

    Column (modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ToolbarWithTitle(stringResource(id = R.string.report_a_problem)) {
            navBack()
        }

        Column(
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {

            Text(
                text = stringResource(R.string.sub_report_problem_screen),
                color = IconsDark,
                fontFamily = Inter,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                modifier = Modifier
                    .padding(top = 30.dp)
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
                    Text(stringResource(R.string.description), style = TextStyle(color = HintText, fontSize = 16.sp, fontFamily = Inter, fontWeight = FontWeight.Normal))
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

            if (selectedImageUri != null) {

                Row(
                modifier = Modifier.padding(top = 10.dp)
            ) {

                    Box(
                        modifier = Modifier.clip(shape = RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        AsyncImage(
                            model = selectedImageUri,
                            contentDescription = null,
                            modifier = Modifier.size(101.dp),
                            contentScale = ContentScale.Crop
                        )

                        IconButton(onClick = {
                            selectedImageUri = null
                        }, modifier = Modifier.offset(x = 10.dp, y = (-10).dp)) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_delete_screen),
                                contentDescription = "",
                                modifier = Modifier.size(18.dp),
                                contentScale = ContentScale.Fit
                            )
                        }
                    }
                }

            }

            IconButton(onClick = {
                multiplePhotoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }) {
               Row {
                   Icon(
                       painter = painterResource(id = R.drawable.ic_add_image),
                       tint = MainOrange,
                       contentDescription = "",
                       modifier = Modifier
                           .size(24.dp)
                   )

                   Text(
                       text = if (selectedImageUri == null) stringResource(R.string.add_screenshot) else stringResource(R.string.change_screenshot),
                       color = MainOrange,
                       fontFamily = Inter,
                       fontWeight = FontWeight.Normal,
                       fontSize = 14.sp,
                       modifier = Modifier.padding(start = 10.dp)
                   )
               }
            }

            DefaultButton(text = stringResource(id = R.string.send), modifier = Modifier.padding(top = 10.dp), pressed = {
                if (text.value.isNotEmpty()) {
                    makeSuggestion(
                        Const.S_FEEDBACK_NAME,
                        Const.S_FEEDBACK, text.value,
                        Const.S_ANDROID
                    )

                    text.value = ""
                    selectedImageUri = null

                    Toast.makeText(context, "We will carefully review your report. Thank you for making us better! :)", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Please describe your problem, we will try to fix it. Thank you! :)", Toast.LENGTH_SHORT).show()
                }

            })

        }
    }
}

@Preview
@Composable
fun ReportProblemScreen_Preview() {
    ReportProblemScreenContent(navBack = {

    }, makeSuggestion = { _, _, _, _ ->

    })
}