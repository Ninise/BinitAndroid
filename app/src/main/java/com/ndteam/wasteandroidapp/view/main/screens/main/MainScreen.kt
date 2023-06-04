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
import androidx.compose.ui.unit.Dp
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
                .horizontalScroll(state = rememberScrollState(), enabled = true)
                .align(alignment = CenterHorizontally)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {
            val recycle = garbageCategories[0]
            val organic = garbageCategories[1]
            val waste = garbageCategories[2]
            val eWaste = garbageCategories[3]
            val hazarWaste = garbageCategories[4]

            GarbageTypeCard(recycle, typeImage = R.drawable.ic_main_recycle_bin) {
                navigate(MainScreens.GarbageDetailsScreen.withArgs(recycle.type.name))
            }


            GarbageTypeCard(organic, typeImage = R.drawable.ic_main_organic_bin) {
                navigate(MainScreens.GarbageDetailsScreen.withArgs(organic.type.name))
            }

            GarbageTypeCard(waste, typeImage = R.drawable.ic_main_garbage_bin) {
                navigate(MainScreens.GarbageDetailsScreen.withArgs(waste.type.name))
            }

            GarbageTypeCard(eWaste, typeImage = R.drawable.ic_main_e_waste_bin) {
                navigate(MainScreens.GarbageDetailsScreen.withArgs(waste.type.name))
            }

            GarbageTypeCard(hazarWaste, typeImage = R.drawable.ic_main_hazar_bing) {
                navigate(MainScreens.GarbageDetailsScreen.withArgs(waste.type.name))
            }
        }

        TextTitleMain(title = stringResource(R.string.main_title_game), topMargin = 15.dp)

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(230.dp)
            .padding(horizontal = 16.dp)
            .background(color = MainBlue, shape = RoundedCornerShape(10.dp))
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
                    .clip(shape = RoundedCornerShape(10.dp))
            )
        }

        TextTitleMain(title = stringResource(R.string.main_title_good_to_know))

        articles.forEach { article ->
           ArticleItem(article = article, onItemClick = {

           })
        }

    }
}

@Composable
fun TextTitleMain(title: String, topMargin: Dp = 25.dp) {
    Text(
        text = title,
        color = TitleText,
        fontFamily = Inter,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        modifier = Modifier.padding(start = 16.dp, top = topMargin, bottom = 10.dp)
    )
}

@Composable
fun GarbageTypeCard(item: GarbageCategory, typeImage: Int, onItemClick: (GarbageCategory) -> Unit) {
    Column (modifier = Modifier
        .clickable {
            onItemClick(item)
        }
        .padding(end = 16.dp)
        .testTag("garbage_type_card")) {

        Box (modifier = Modifier
            .size(116.dp, 116.dp)
            .background(color = MainCardBack, shape = RoundedCornerShape(10.dp))
        ) {
            Image(
                painter = painterResource(id = typeImage),
                contentDescription = "Bin",
                modifier = Modifier
                    .width(70.dp)
                    .height(70.dp)
                    .align(alignment = Alignment.Center),
                contentScale = ContentScale.Fit)
        }

        Text(
            text = item.title,
            color = TitleText,
            fontFamily = Inter,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            modifier = Modifier
                .align(alignment = CenterHorizontally)
                .padding(top = 5.dp))

    }
}

@Composable
fun ArticleItem(article: Article, onItemClick: (Article) -> Unit) {
    Row (modifier = Modifier
        .padding(start = 16.dp, end = 16.dp, bottom = 10.dp)
        .clickable {
            onItemClick(article)
        }) {
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

    Divider(startIndent = 2.dp, thickness = 1.dp, color = DividerColor, modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 10.dp))
}

@Preview
@Composable
fun MainPreview() {
    MainScreenContent(
        searchSuggestions = listOf("meat", "cup", "banana"),
        garbageCategories = listOf(GarbageCategory("Recycle", "", RecycleType.GARBAGE, "", ""), GarbageCategory("Organic", "0", RecycleType.ORGANIC, "", ""), GarbageCategory("Waste", "", RecycleType.GARBAGE, "", "")),
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