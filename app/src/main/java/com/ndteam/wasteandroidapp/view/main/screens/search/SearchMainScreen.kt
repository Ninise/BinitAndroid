package com.ndteam.wasteandroidapp.view.main.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.FirebaseApp
import com.ndteam.wasteandroidapp.api.WasteApi
import com.ndteam.wasteandroidapp.models.GarbageItem
import com.ndteam.wasteandroidapp.models.states.SuggestionState
import com.ndteam.wasteandroidapp.repository.WasteRepository
import com.ndteam.wasteandroidapp.repository.WasteRepositoryImpl
import com.ndteam.wasteandroidapp.ui.theme.*
import com.ndteam.wasteandroidapp.view.main.MainViewModel

@Composable
fun SearchMainScreen(navController: NavController, viewModel: MainViewModel, query: String = "") {

    val textState = remember { mutableStateOf(TextFieldValue(query)) }

    val searchSuggestions = viewModel.suggestionState.value.suggestions ?: listOf()

    viewModel.searchGarbage(textState.value.text)

    Box(modifier =
    Modifier
        .fillMaxSize()
        .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            SearchView(state = textState, click = {

            }, backClick = {
                navController.popBackStack()
            })

            if (textState.value.text.isEmpty()) {
                LazyRow(modifier = Modifier.padding(start = 15.dp)) {
                    items(searchSuggestions) {
                        SearchChip(text = it, onItemClick = {
                            textState.value = TextFieldValue(it)
                        })

                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn(modifier = Modifier.padding(horizontal = 20.dp)) {

                if (viewModel.garbageItemState.value.isLoading ?: false) {
                    // showLoader
                } else {
                    viewModel.garbageItemState.value.garbageList?.let {
                        items(it) { item ->

                            GarbageItemView(
                                item = item,
                                onItemClick = {

                                }
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Divider(startIndent = 20.dp, thickness = 1.dp, color = DividerColor)

                            Spacer(modifier = Modifier.height(4.dp))
                        }
                    }

                }
            }
        }
    }

}

@Composable
fun SearchView(state: MutableState<TextFieldValue>, isMockView: Boolean = false, click: () -> Unit, backClick: () -> Unit = {}) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
        .clickable {
            if (isMockView) {
                click()
            }
        }
        .testTag("search_view")) {

        if (state.value == TextFieldValue("") && !isMockView) {
            IconButton(onClick = {
                backClick()
            }) {
                Icon(
                    Icons.Default.ArrowBack,
                    tint = IconsGray,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(start = 15.dp)
                        .size(24.dp)
                )
            }
        }

        TextField(
            value = state.value,
            onValueChange = { value ->
                state.value = value
            },
            enabled = !isMockView,
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            placeholder = {
                Text("Search", style = TextStyle(color = BodyText, fontSize = 16.sp))
            },
            textStyle = TextStyle(color = BodyText, fontSize = 16.sp),
            leadingIcon = {
                if (state.value != TextFieldValue("")) {
                    IconButton(onClick = {
                        backClick()
                    }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            tint = IconsGray,
                            contentDescription = "",
                            modifier = Modifier
                                .padding(15.dp)
                                .size(24.dp)
                        )
                    }
                } else {
                    Icon(
                        Icons.Default.Search,
                        tint = IconsGray,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }

            },
            trailingIcon = {
                if (state.value != TextFieldValue("")) {
                    IconButton(
                        onClick = {
                            state.value =
                                TextFieldValue("") // Remove text from TextField when you press the 'X' icon
                        }
                    ) {
                        Icon(
                            Icons.Default.Close,
                            tint = IconsGray,
                            contentDescription = "",
                            modifier = Modifier
                                .padding(15.dp)
                                .size(24.dp)
                        )
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(16.dp), // The TextFiled has rounded corners top left and right by default
            colors = TextFieldDefaults.textFieldColors(
                textColor = BodyText,
                cursorColor = BodyText,
                leadingIconColor = IconsGray,
                trailingIconColor = Color.White,
                backgroundColor = SearchBackGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
    }
}

@Composable
fun SearchChip(text: String, onItemClick: (String) -> Unit) {
    Box(modifier = Modifier
        .padding(horizontal = 4.dp)
        .testTag("search_chip")
        .background(color = ChipBackGray, shape = RoundedCornerShape(10.dp))
        .clickable { onItemClick(text) }) {
        Text(
            text = text,
            color = BodyText,
            style = MaterialTheme.typography.body1,
            fontSize = 12.sp,
            letterSpacing = 1.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun GarbageItemView(item: GarbageItem, showIcon: Boolean = true, onItemClick: (String) -> Unit) {
   Row(modifier = Modifier
       .fillMaxWidth()
       .padding(vertical = 10.dp)
       .clickable {
           onItemClick(item.name)
       }) {
       AsyncImage(
           model = item.icon,
           contentDescription = item.name,
           contentScale = ContentScale.Crop,
           modifier = Modifier
               .size(76.dp, height = 76.dp)
               .clip(RoundedCornerShape(11.dp))

       )

       Column (
           Modifier
               .weight(1f)
               .padding(horizontal = 15.dp)) {
           Text(
               text = item.name,
               color = TitleText,
               fontFamily = Inter,
               fontWeight = FontWeight.Medium,
               fontSize = 14.sp,
               letterSpacing = 1.sp,
           )

           Spacer(modifier = Modifier.height(2.dp))

           Text(
               text = item.wayToRecycler,
               color = BodyText,
               fontFamily = Inter,
               fontWeight = FontWeight.Normal,
               fontSize = 12.sp,
               letterSpacing = 1.sp,
           )
       }


       if (showIcon) {
           Icon(
               painterResource(id = item.returnImage()),
               tint = GarbageTypeIconColor,
               contentDescription = "",
               modifier = Modifier
                   .padding(end = 15.dp)
                   .size(25.dp)
                   .align(Alignment.CenterVertically),
           )
       }

   }
}

@Preview(showBackground = true)
@Composable
fun SearchViewPreview() {

    SearchMainScreen(
        navController = NavController(LocalContext.current),
        viewModel = MainViewModel(WasteRepositoryImpl(WasteApi)),
        query = ""
    )
}