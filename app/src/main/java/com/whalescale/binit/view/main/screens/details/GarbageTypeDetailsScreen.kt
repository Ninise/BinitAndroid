package com.whalescale.binit.view.main.screens.search

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.whalescale.binit.R
import com.whalescale.binit.models.GarbageCategory
import com.whalescale.binit.models.GarbageItem
import com.whalescale.binit.models.RecycleType
import com.whalescale.binit.models.states.GarbageItemState
import com.whalescale.binit.ui.theme.DividerColor
import com.whalescale.binit.ui.theme.IconsDark
import com.whalescale.binit.ui.theme.Inter
import com.whalescale.binit.ui.theme.MainOrange
import com.whalescale.binit.utils.Utils
import com.whalescale.binit.utils.ViewUtils
import com.whalescale.binit.view.custom_views.CircularLoaderView
import com.whalescale.binit.view.main.MainViewModel
import com.whalescale.binit.view.main.navigation.MainScreens


// expand arrow goes to right +
// limit elements to 5 and disable internal scroll +
// add author +
// rename categories +
// make see all clickable +

@Composable
fun GarbageTypeDetailsScreen(navController: NavController, viewModel: MainViewModel, garbageCategory: GarbageCategory) {

    LaunchedEffect(Unit, block = {
        viewModel.searchGarbage(garbageCategory.type, limit = 5)
    })

    GarbageTypeDetailsScreenContent(garbageCategory = garbageCategory, garbageItemState = viewModel.garbageItemState.value, navigate = { destination ->
        if (destination == null) {
            navController.popBackStack()
            viewModel.clearGarbageList()
        } else {
            destination.let {
                navController.navigate(MainScreens.SearchMainScreen.withArgs(it))
            }
        }
    }, openLink = { link ->
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        navController.context.startActivity(browserIntent)
    })
}

@Composable
fun GarbageTypeDetailsScreenContent(garbageCategory: GarbageCategory, garbageItemState: GarbageItemState, navigate: (String?) -> Unit, openLink: (String) -> Unit) {

    val scrollState = rememberScrollState()

    Column (modifier = Modifier
        .background(color = Color.White)
        .verticalScroll(scrollState)) {
        ToolbarPlanIconAndTitle(listType = garbageCategory.title) {
            navigate(null)
        }

        AsyncImage(
            model = garbageCategory.image,
            contentDescription = garbageCategory.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp)
                .padding(vertical = 10.dp, horizontal = 15.dp)
        )


        Text(
            text = "@${garbageCategory.image_author}",
            fontSize = 12.sp,
            fontFamily = Inter,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Italic,
            textDecoration = TextDecoration.Underline,
            color = IconsDark,
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .clickable {
                    openLink(garbageCategory.image_author_url)
                }
        )

        DetailsTextView(
            descriptionText = garbageCategory.description,
        )

        Column (
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(vertical = 20.dp)
                ) {

            garbageCategory.items.forEach {
                Column {
                    val expanded = remember { mutableStateOf(false) }

                    Divider(
                        color = DividerColor,
                        modifier = Modifier.padding(vertical = 5.dp)
                    )

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

                    AnimatedVisibility(visible = expanded.value) {
                        Column {
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
                                        modifier = Modifier)
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
                    }


                }
            }


            Divider(
                color = DividerColor,
                modifier = Modifier.padding(top = 5.dp)
            )
        }


        if (garbageItemState.isLoading ?: false) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularLoaderView(color = garbageCategory.categoryColor())
            }
        } else {
            garbageItemState.garbageList?.let {
                GarbageExampleListDetailsView(
                    Utils.getCategoryTitleByType(garbageCategory.type),
                    it,
                    navigate = {
                        navigate(garbageCategory.type)
                    }
                )
            }
        }
    }

}

@Composable
fun ToolbarPlanIconAndTitle(listType: String, backPressed: () -> Unit) {

        IconButton(onClick = {
            backPressed()
        }) {
            Icon(
                Icons.Default.ArrowBack,
                tint = IconsDark,
                contentDescription = "",
                modifier = Modifier
                    .size(24.dp)
            )
        }

        if (listType.isNotEmpty()) {
            Text(
                text = listType,
                fontSize = 32.sp,
                fontFamily = Inter,
                fontWeight = FontWeight.Bold,
                color = IconsDark,
                modifier = Modifier.padding(horizontal = 15.dp)
            )
        }

}

@Composable
fun DetailsTextView(descriptionText: String) {

    Text(
        text = ViewUtils.parseString(input = descriptionText),
        color = IconsDark,
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        modifier = Modifier.padding(top = 10.dp, start = 16.dp, end = 16.dp)
    )
}

@Composable
fun GarbageExampleListDetailsView(type: String, garbage: List<GarbageItem>, navigate: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(
            top = 30.dp,
            start = 16.dp,
            end = 16.dp)
    ) {
        Text(
            text = "Products for $type bin:",
            color = IconsDark,
            fontFamily = Inter,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            letterSpacing = 1.sp,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        TextButton(onClick = {
            navigate()
        }) {
            Text(
                text = stringResource(R.string.see_all),
                color = MainOrange,
                fontFamily = Inter,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                letterSpacing = 1.sp
            )
        }

    }

    LazyColumn(modifier = Modifier
        .height(600.dp)
        .padding(horizontal = 16.dp)
    ) {
        items(garbage) {

            GarbageItemView(
                item = it,
                onItemClick = {

                },
                showIcon = false
            )

            Spacer(modifier = Modifier.height(4.dp))

            Divider(startIndent = 40.dp, thickness = 1.dp, color = DividerColor)

            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GarbageTypeDetailsScreenPreview() {
    GarbageTypeDetailsScreenContent(
        garbageCategory = GarbageCategory("Recycle", "https://imageio.forbes.com/specials-images/imageserve/623026466/0x0.jpg?format=jpg","Very imp", "good desk", "", "", "",items = listOf()),
    garbageItemState = GarbageItemState(garbageList = arrayListOf(GarbageItem("", "What", "Recycle", RecycleType.RECYCLE.name))),
        navigate = {

        }, {

        }
    )
}