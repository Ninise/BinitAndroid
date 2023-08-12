package com.ndteam.wasteandroidapp.view.main.screens.article

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ndteam.wasteandroidapp.R
import com.ndteam.wasteandroidapp.models.responses.Article
import com.ndteam.wasteandroidapp.ui.theme.DividerColor
import com.ndteam.wasteandroidapp.ui.theme.IconsDark
import com.ndteam.wasteandroidapp.ui.theme.Inter
import com.ndteam.wasteandroidapp.ui.theme.MainOrange
import com.ndteam.wasteandroidapp.utils.ViewUtils
import com.ndteam.wasteandroidapp.view.main.MainViewModel
import com.ndteam.wasteandroidapp.view.main.screens.search.DetailsTextView
import com.ndteam.wasteandroidapp.view.main.screens.search.ToolbarPlanIconAndTitle


@Composable
fun ArticleDetailsScreen(navController: NavController, viewModel: MainViewModel, article: Article) {

    ArticleDetailsScreenContent(article = article, navigate = { destination ->
        if (destination == null) {
            navController.popBackStack()
            viewModel.clearGarbageList()
        }
    })
}

@Composable
fun ArticleDetailsScreenContent(article: Article, navigate: (String?) -> Unit) {

    val scrollState = rememberScrollState()

    Column (modifier = Modifier
        .background(color = Color.White)
        .verticalScroll(scrollState)) {
        ToolbarPlanIconAndTitle(listType = article.title) {
            navigate(null)
        }

        AsyncImage(
            model = article.image,
            contentDescription = article.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp)
                .padding(vertical = 10.dp, horizontal = 15.dp)
        )

        Text(
            text = "@${article.image_author}",
            fontSize = 12.sp,
            fontFamily = Inter,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Italic,
            color = IconsDark,
            modifier = Modifier
                .padding(horizontal = 15.dp)
        )

        DetailsTextView(
            descriptionText = article.description,
        )

        Column (
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(vertical = 20.dp)
        ) {
            Divider(
                color = DividerColor
            )
            article.items.forEach {
                Column {
                    val expanded = remember { mutableStateOf(false) }

                    Row(modifier = Modifier
                        .clickable { expanded.value = !expanded.value }
                        .height(60.dp)
                        .padding(end = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = it.title,
                            fontSize = 16.sp,
                            fontFamily = Inter,
                            fontWeight = FontWeight.Normal,
                            color = MainOrange,
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 10.dp)
                        )

                        Image(
                            painter = painterResource(id = if (expanded.value) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down),
                            contentDescription = "toggle",
                            modifier = Modifier
                                .size(16.dp)
                        )
                    }

                    if (expanded.value) {
                        it.data.forEach {
                            Row (modifier = Modifier
                                .padding(horizontal = 20.dp)
                                .padding(vertical = 5.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Text(text = "• ",
                                    fontSize = 16.sp,
                                    fontFamily = Inter,
                                    fontWeight = FontWeight.Normal,
                                    color = IconsDark,
                                    modifier = Modifier
                                )
                                Text(
                                    text = ViewUtils.parseString(input = it),
                                    fontSize = 16.sp,
                                    fontFamily = Inter,
                                    fontWeight = FontWeight.Normal,
                                    color = IconsDark
                                )
                            }
                        }
                    }

                    Divider(
                        color = DividerColor,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }
            }
        }
    }

}
