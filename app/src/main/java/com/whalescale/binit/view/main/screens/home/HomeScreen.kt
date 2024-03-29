package com.whalescale.binit.view.main.screens.home

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.whalescale.binit.R
import com.whalescale.binit.models.GarbageCategory
import com.whalescale.binit.models.responses.Article
import com.whalescale.binit.ui.theme.*
import com.whalescale.binit.utils.Const
import com.whalescale.binit.utils.Utils
import com.whalescale.binit.view.main.MainViewModel
import com.whalescale.binit.view.main.navigation.MainScreens
import com.whalescale.binit.view.main.screens.search.SearchChip
import com.whalescale.binit.view.main.screens.search.SearchView
import kotlinx.coroutines.delay


@Composable
fun HomeScreen(viewModel: MainViewModel, navigation: (String) -> Unit) {

    val searchSuggestions = viewModel.suggestionState.value.suggestions ?: listOf()

    val garbageCategories = viewModel.garbageState.value.garbageList ?: listOf()

    val articles = viewModel.articleItemState.value.articlesList ?: listOf()

    HomeScreenContent(searchSuggestions, garbageCategories, articles,
        navigate = { query ->
            navigation(query)
        }
    )

}

@Composable
fun HomeScreenContent(searchSuggestions: List<String>, garbageCategories: List<GarbageCategory>, articles: List<Article>, navigate: (String) -> Unit) {

    val textState = remember { mutableStateOf(TextFieldValue("")) }

    Column(modifier = Modifier
        .fillMaxSize()
        .testTag("test_main")
        .background(Color.White)
        .verticalScroll(state = rememberScrollState(), enabled = true)) {

        TextTitleMain(title = stringResource(R.string.main_title_what_to_dispose))

        SearchView(state = textState, isMockView = true, paddingTop = 5.dp, click = {
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

        val listState = rememberLazyListState()

        LazyRow (
            modifier = Modifier
                .align(alignment = CenterHorizontally)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            state = listState,
            horizontalArrangement = Arrangement.SpaceBetween) {

            items(garbageCategories) {
                GarbageTypeCard(it, typeImage = it.categoryBinImage()) {
                    navigate(MainScreens.GarbageDetailsScreen.withArgs(it.type))
                }
            }
        }

        LaunchedEffect(key1 = Unit, block = {
            Utils.log("test")
            if (!Utils.isListWasAnimated()) {
                Utils.log("test wasnt animated")
                delay(500)
                listState.animateScrollBy(value = 50f)
                delay(500)
                listState.animateScrollToItem(index = 0)
                delay(500)
                listState.animateScrollBy(value = 50f)
                delay(500)
                listState.animateScrollToItem(index = 0)
            }
            Utils.log("test save state")
            Utils.saveAnimationState(true)
        })

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
               navigate(MainScreens.ArticleDetailsScreen.withArgs("${it.id}"))
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
        .width(width = 100.dp)
        .padding(end = 10.dp)
        .testTag("garbage_type_card"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box (modifier = Modifier
            .size(90.dp, 90.dp)
            .background(color = MainCardBack, shape = RoundedCornerShape(10.dp))
        ) {
            Image(
                painter = painterResource(id = typeImage),
                contentDescription = "Bin",
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .align(alignment = Alignment.Center),
                contentScale = ContentScale.Fit)
        }

        Text(
            text = item.display_type,
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
                text = article.short_description,
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
fun HomePreview() {
    HomeScreenContent(
        searchSuggestions = listOf("meat", "cup", "banana"),
        garbageCategories = listOf(
            GarbageCategory("Recycle", "R.drawable.ic_recycle",
                Const.RECYCLE_TYPE, "", "", "", "", items = listOf()),
            GarbageCategory("Garbage", "R.drawable.ic_garbage",
                Const.GARBAGE_TYPE, "", "", "","", items = listOf())
        ),
        articles = listOf(),
        navigate = {

        }
    )
}