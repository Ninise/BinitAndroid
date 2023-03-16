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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ndteam.wasteandroidapp.R
import com.ndteam.wasteandroidapp.models.GarbageCategory
import com.ndteam.wasteandroidapp.models.RecycleType
import com.ndteam.wasteandroidapp.ui.theme.MainBlue
import com.ndteam.wasteandroidapp.ui.theme.Nunito
import com.ndteam.wasteandroidapp.ui.theme.TitleText
import com.ndteam.wasteandroidapp.view.main.MainViewModel
import com.ndteam.wasteandroidapp.view.main.navigation.MainScreens
import com.ndteam.wasteandroidapp.view.main.screens.search.GarbageTypeDetailsScreen
import com.ndteam.wasteandroidapp.view.main.screens.search.SearchChip
import com.ndteam.wasteandroidapp.view.main.screens.search.SearchView


@Composable
fun MainScreen(navController: NavController, viewModel: MainViewModel) {

    val textState = remember { mutableStateOf(TextFieldValue("")) }

    viewModel.downloadData()

    val searchSuggestions = viewModel.suggestionState.value.suggestions

    val garbageCategories = viewModel.garbageState.value.garbageList

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        SearchView(state = textState, showBack = false, click = {
            navController.navigate(MainScreens.SearchMainScreen.route)
        })

        searchSuggestions?.let {
            LazyRow(modifier = Modifier.padding(start = 15.dp)) {
                items(searchSuggestions) {
                    SearchChip(text = it, onItemClick = {
                        navController.navigate(MainScreens.SearchMainScreen.route)
                    })

                }
            }
        }

        TextTitleMain(title = stringResource(R.string.main_title_how_to_sort))
        
        Row (modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(start = 10.dp)) {

            garbageCategories?.let {
                for (i in garbageCategories.indices) {
                    GarbageTypeCard(garbageCategories[i]) {
                        navController.navigate(MainScreens.GarbageDetailsScreen.withArgs(garbageCategories[i].type.name))
                    }
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
        .width(180.dp)
        .height(180.dp)
        .background(color = Color.Transparent, shape = RoundedCornerShape(16.dp))
        .padding(end = 10.dp)) {

        Image(
            painter = painterResource(id = item.image),
            contentDescription = "Back city type",
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop)

        Image(
            painter = painterResource(id = item.returnShadow()),
            contentDescription = "Back shadow type",
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .padding(top = 5.dp)
                .align(alignment = Alignment.BottomCenter)
                .clip(RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp)))


        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 15.dp)
                .align(alignment = Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically) {

            Text(
                text = item.title,
                color = Color.White,
                fontFamily = Nunito,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )
            
            Spacer(modifier = Modifier.weight(1f))

            Icon(
                painterResource(id = item.returnImage()),
                tint = Color.White,
                contentDescription = "",
                modifier = Modifier
                    .size(25.dp)
                    .align(Alignment.CenterVertically),
            )

        }


    }
}

@Preview
@Composable
fun MainPreview() {
    MainScreen(navController = NavController(LocalContext.current), viewModel = viewModel())
}