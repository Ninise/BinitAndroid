package com.ndteam.wasteandroidapp.view.main.screens.settings

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ndteam.wasteandroidapp.R
import com.ndteam.wasteandroidapp.ui.theme.*
import com.ndteam.wasteandroidapp.view.custom_views.DefaultButton

@Composable
fun ReportProblemScreen(navBack: () -> Unit) {
    ReportProblemScreenContent(navBack)
}

@Composable
fun ReportProblemScreenContent(navBack: () -> Unit) {

    val text = remember { mutableStateOf("") }

    var selectedImageUris by remember {
        mutableStateOf<List<Uri>>(emptyList())
    }

    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris -> selectedImageUris = uris }
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

            LazyRow(
                modifier = Modifier.padding(top = 10.dp)
            ) {

                items(selectedImageUris) { uri ->
                   Box(
                       modifier = Modifier.clip(shape = RoundedCornerShape(10.dp)),
                       contentAlignment = Alignment.TopEnd
                   ) {
                       AsyncImage(
                           model = uri,
                           contentDescription = null,
                           modifier = Modifier.size(101.dp),
                           contentScale = ContentScale.Crop
                       )

                       IconButton(onClick = {
                           selectedImageUris = selectedImageUris.filter { it != uri }
                       }, modifier = Modifier.offset(x = 10.dp, y = (-10).dp)) {
                           Image(
                               painter = painterResource(id = R.drawable.ic_delete_screen),
                               contentDescription = "",
                               modifier = Modifier.size(18.dp),
                               contentScale = ContentScale.Fit
                           )
                       }
                   }

                    Spacer(
                        modifier = Modifier.width(10.dp)
                    )
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
                       text = stringResource(R.string.add_screenshot),
                       color = MainOrange,
                       fontFamily = Inter,
                       fontWeight = FontWeight.Normal,
                       fontSize = 14.sp,
                       modifier = Modifier.padding(start = 10.dp)
                   )
               }
            }

            DefaultButton(text = stringResource(id = R.string.send), modifier = Modifier.padding(top = 10.dp), pressed = {

            })

        }
    }
}

@Preview
@Composable
fun ReportProblemScreen_Preview() {
    ReportProblemScreenContent {

    }
}