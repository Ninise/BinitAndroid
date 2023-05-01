package com.ndteam.wasteandroidapp.view.main.screens.main

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ndteam.wasteandroidapp.R
import com.ndteam.wasteandroidapp.models.GarbageCategory
import com.ndteam.wasteandroidapp.ui.theme.*
import com.ndteam.wasteandroidapp.utils.Const
import com.ndteam.wasteandroidapp.view.main.MainViewModel
import com.ndteam.wasteandroidapp.view.main.navigation.MainScreens
import com.ndteam.wasteandroidapp.view.main.screens.search.SearchChip
import com.ndteam.wasteandroidapp.view.main.screens.search.SearchView


@Composable
fun MainScreen(navController: NavController, viewModel: MainViewModel) {

    val textState = remember { mutableStateOf(TextFieldValue("")) }

    val searchSuggestions = viewModel.suggestionState.value.suggestions

    val garbageCategories = viewModel.garbageState.value.garbageList

    Column(modifier = Modifier
        .fillMaxSize()
        .testTag("test_main")
        .background(Color.White)
        .verticalScroll(state = rememberScrollState(), enabled = true)) {
        SearchView(state = textState, isMockView = true, click = {
            navController.navigate(MainScreens.SearchMainScreen.withArgs(Const.SEARCH_QUERY_DEFAULT))
        })

        searchSuggestions?.let {
            LazyRow(modifier = Modifier.padding(start = 15.dp)) {
                items(searchSuggestions) {
                    SearchChip(text = it, onItemClick = { text ->
                        navController.navigate(MainScreens.SearchMainScreen.withArgs(text))
                    })

                }
            }
        }

        TextTitleMain(title = stringResource(R.string.main_title_how_to_sort))
        
        LazyRow (modifier = Modifier
            .padding(start = 10.dp)) {

            items(garbageCategories!!) {category ->
                GarbageTypeCard(category) {
                    navController.navigate(MainScreens.GarbageDetailsScreen.withArgs(category.type.name))
                }
            }

        }

        TextTitleMain(title = stringResource(R.string.main_title_game))

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(230.dp)
            .padding(horizontal = 10.dp)
            .background(color = MainBlue, shape = RoundedCornerShape(16.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_game_placeholder),
                contentDescription = "Game placeholder",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp)
                    .clip(shape = RoundedCornerShape(16.dp))
            )
        }

        TextTitleMain(title = stringResource(R.string.main_title_good_to_know))

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(color = MainOrange)
            .padding(horizontal = 10.dp))

    }
}

@Composable
fun TextTitleMain(title: String) {
    Text(
        text = title,
        color = TitleText,
        fontFamily = Nunito,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        modifier = Modifier.padding(start = 16.dp, top = 20.dp, bottom = 10.dp)
    )
}

@Composable
fun GarbageTypeCard(item: GarbageCategory, onItemClick: (GarbageCategory) -> Unit) {
    Box (modifier = Modifier
        .clickable {
            onItemClick(item)
        }
        .testTag("garbage_type_card")
        .width(160.dp)
        .height(180.dp)
        .background(color = Color.Transparent, shape = RoundedCornerShape(16.dp))
        .padding(end = 10.dp)) {

        Image(
            painter = painterResource(id = item.returnShadow()),
            contentDescription = "Back shadow type",
            modifier = Modifier
                .width(160.dp)
                .height(180.dp)
                .align(alignment = Alignment.Center)
                .clip(RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp)))

       Column (modifier = Modifier
           .align(alignment = Alignment.Center)
           .fillMaxWidth()) {

           Image(
               painter = painterResource(id = item.image),
               contentDescription = "Back city type",
               modifier = Modifier
                   .width(108.dp)
                   .height(108.dp)
                   .align(alignment = Alignment.CenterHorizontally)
                   .clip(RoundedCornerShape(16.dp)),
               contentScale = ContentScale.Fit)

           Spacer(modifier = Modifier.height(30.dp))
       }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 15.dp)
                .align(alignment = Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically) {

            Text(
                text = item.title,
                color = Color.White,
                fontFamily = Inter,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
            )
            
            Spacer(modifier = Modifier.weight(1f))

            Icon(
                painterResource(id = item.returnImage()),
                tint = Color.White,
                contentDescription = "",
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.CenterVertically),
            )

        }


    }
}

@Preview
@Composable
fun MainPreview() {
    MainScreen(navController = NavController(LocalContext.current), viewModel = hiltViewModel())
}