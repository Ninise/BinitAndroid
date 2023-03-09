package com.ndteam.wasteandroidapp.view.main.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ndteam.wasteandroidapp.view.main.navigation.MainScreens
import com.ndteam.wasteandroidapp.view.main.screens.search.SearchChip
import com.ndteam.wasteandroidapp.view.main.screens.search.SearchView

@Composable
fun MainScreen(navController: NavController?) {
    val textState = remember { mutableStateOf(TextFieldValue("")) }

    val searchSuggestions = arrayListOf<String>("plastic bag", "meat", "cup", "pan", "banana")


    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        SearchView(state = textState, showBack = false, click = {
            navController?.navigate(MainScreens.SearchMainScreen.route)
        })

        LazyRow(modifier = Modifier.padding(start = 15.dp)) {
            items(searchSuggestions) {
                SearchChip(text = it, onItemClick = {
                    navController?.navigate(MainScreens.SearchMainScreen.route)
                })

            }
        }


    }
}

@Preview
@Composable
fun MainPreview() {
    MainScreen(navController = null)
}