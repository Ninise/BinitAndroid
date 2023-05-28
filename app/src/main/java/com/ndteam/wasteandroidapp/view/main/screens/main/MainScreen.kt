package com.ndteam.wasteandroidapp.view.main.screens.main

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
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
import coil.compose.AsyncImage
import com.ndteam.wasteandroidapp.R
import com.ndteam.wasteandroidapp.models.Article
import com.ndteam.wasteandroidapp.models.GarbageCategory
import com.ndteam.wasteandroidapp.models.RecycleType
import com.ndteam.wasteandroidapp.ui.theme.*
import com.ndteam.wasteandroidapp.utils.Const
import com.ndteam.wasteandroidapp.utils.Utils
import com.ndteam.wasteandroidapp.view.main.MainViewModel
import com.ndteam.wasteandroidapp.view.main.navigation.MainScreens
import com.ndteam.wasteandroidapp.view.main.screens.search.SearchChip
import com.ndteam.wasteandroidapp.view.main.screens.search.SearchView


@Composable
fun MainScreen(navController: NavController, viewModel: MainViewModel) {



    val searchSuggestions = viewModel.suggestionState.value.suggestions ?: listOf()

    val garbageCategories = viewModel.garbageState.value.garbageList ?: listOf()

    val articles = viewModel.articleItemState.value.articlesList ?: listOf()

    MainScreenContent(searchSuggestions, garbageCategories, articles,
        navigate = { destination ->
            navController.navigate(destination)
        }
    )
}

@Composable
fun MainScreenContent(searchSuggestions: List<String>, garbageCategories: List<GarbageCategory>, articles: List<Article>, navigate: (String) -> Unit) {

    val textState = remember { mutableStateOf(TextFieldValue("")) }

    Column(modifier = Modifier
        .fillMaxSize()
        .testTag("test_main")
        .background(Color.White)
        .verticalScroll(state = rememberScrollState(), enabled = true)) {
        SearchView(state = textState, isMockView = true, click = {
            navigate(MainScreens.SearchMainScreen.withArgs(Const.SEARCH_QUERY_DEFAULT))
        }, onTextChange = {

        })

        LazyRow(modifier = Modifier.padding(start = 10.dp)) {
            items(searchSuggestions) {
                SearchChip(text = it, onItemClick = { text ->
                    navigate(MainScreens.SearchMainScreen.withArgs(text))
                })

            }
        }

        TextTitleMain(title = stringResource(R.string.main_title_how_to_sort))

        Row (
            modifier = Modifier
                .align(alignment = CenterHorizontally)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {
            val recycle = garbageCategories[0]
            val organic = garbageCategories[1]
            val waste = garbageCategories[2]

            GarbageTypeCard(recycle) {
                navigate(MainScreens.GarbageDetailsScreen.withArgs(recycle.type.name))
            }


            GarbageTypeCard(organic) {
                navigate(MainScreens.GarbageDetailsScreen.withArgs(organic.type.name))
            }


            GarbageTypeCard(waste) {
                navigate(MainScreens.GarbageDetailsScreen.withArgs(waste.type.name))
            }
        }

        TextTitleMain(title = stringResource(R.string.main_title_game))

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(230.dp)
            .padding(horizontal = 16.dp)
            .background(color = MainBlue, shape = RoundedCornerShape(16.dp))
            .clickable {
                navigate(MainScreens.GameMainScreen.route)
            }
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

        articles.forEach { article ->
            Row (modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 10.dp)) {
                AsyncImage(
                    model = article.image,
                    contentDescription = "Article icon",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(76.dp, height = 76.dp)
                        .clip(RoundedCornerShape(10.dp))
                )

                Column (modifier = Modifier.padding(horizontal = 12.dp)) {
                    Text(
                        text = article.title,
                        color = TitleText,
                        fontFamily = Inter,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )

                    Text(
                        text = article.shortDesc,
                        color = SubTitleText,
                        fontFamily = Inter,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        maxLines = 2
                    )
                }
            }

            Divider(startIndent = 2.dp, thickness = 1.dp, color = DividerColor, modifier = Modifier.padding(horizontal = 16.dp))
        }



    }
}

@Composable
fun TextTitleMain(title: String) {
    Text(
        text = title,
        color = TitleText,
        fontFamily = Inter,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        modifier = Modifier.padding(start = 16.dp, top = 25.dp, bottom = 10.dp)
    )
}

@Composable
fun GarbageTypeCard(item: GarbageCategory, onItemClick: (GarbageCategory) -> Unit) {
    Box (modifier = Modifier
        .clickable {
            onItemClick(item)
        }
        .testTag("garbage_type_card")
        .background(color = Color.Transparent, shape = RoundedCornerShape(16.dp))
        ) {

        Column (modifier = Modifier.align(alignment = Alignment.Center)) {
            Image(
                painter = painterResource(id = item.returnShadow()),
                contentDescription = "Back shadow type",
                modifier = Modifier
                    .size(115.dp, 115.dp))

            Text(
                text = item.title,
                color = TitleText,
                fontFamily = Inter,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                modifier = Modifier
                    .align(alignment = CenterHorizontally)
                    .padding(top = 5.dp)
            )
        }



//       Column (modifier = Modifier
//           .align(alignment = Alignment.Center)
//           .fillMaxWidth()) {
//
//           Image(
//               painter = painterResource(id = item.image),
//               contentDescription = "Back city type",
//               modifier = Modifier
//                   .width(160.dp)
//                   .height(160.dp)
//                   .align(alignment = Alignment.CenterHorizontally)
//                   .clip(RoundedCornerShape(16.dp)),
//               contentScale = ContentScale.Fit)
//
//           Spacer(modifier = Modifier.height(30.dp))
//       }

//        Row (
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 16.dp, vertical = 15.dp)
//                .align(alignment = Alignment.BottomCenter),
//            verticalAlignment = Alignment.CenterVertically) {
//
//            Text(
//                text = item.title,
//                color = Color.White,
//                fontFamily = Inter,
//                fontWeight = FontWeight.SemiBold,
//                fontSize = 16.sp,
//            )
//
//            Spacer(modifier = Modifier.weight(1f))
//
//            Icon(
//                painterResource(id = item.returnImage()),
//                tint = Color.White,
//                contentDescription = "",
//                modifier = Modifier
//                    .size(20.dp)
//                    .align(Alignment.CenterVertically),
//            )
//
//        }


    }
}

@Preview
@Composable
fun MainPreview() {
    MainScreenContent(
        searchSuggestions = listOf("meat", "cup", "banana"),
        garbageCategories = listOf(GarbageCategory("Recycle", 0, RecycleType.GARBAGE, "", ""), GarbageCategory("Organic", 0, RecycleType.ORGANIC, "", ""), GarbageCategory("Waste", 0, RecycleType.GARBAGE, "", "")),
        articles = listOf(Article(
            image = "https://www.sciencenews.org/wp-content/uploads/2021/01/013021_plastics_feat-1440x700.jpg",
            title = "Reuse. Reduce. Recycle",
            shortDesc = "How to make lifestyle eco-friendly?",
            content = "How to make lifestyle eco-friendly?, \"How to make lifestyle eco-friendly?\", \"How to make lifestyle eco-friendly?\", \"How to make lifestyle eco-friendly?\""),
            Article(
                image = "https://www.sciencenews.org/wp-content/uploads/2021/01/013021_plastics_feat-1440x700.jpg",
                title = "Reuse. Reduce. Recycle",
                shortDesc = "How to make lifestyle eco-friendly?",
                content = "How to make lifestyle eco-friendly?, \"How to make lifestyle eco-friendly?\", \"How to make lifestyle eco-friendly?\", \"How to make lifestyle eco-friendly?\""),
            Article(
                image = "https://www.sciencenews.org/wp-content/uploads/2021/01/013021_plastics_feat-1440x700.jpg",
                title = "Reuse. Reduce. Recycle",
                shortDesc = "How to make lifestyle eco-friendly?",
                content = "How to make lifestyle eco-friendly?, \"How to make lifestyle eco-friendly?\", \"How to make lifestyle eco-friendly?\", \"How to make lifestyle eco-friendly?\""),),
        navigate = {

        }
    )
}