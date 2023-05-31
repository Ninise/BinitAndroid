package com.ndteam.wasteandroidapp.view.main.screens.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ndteam.wasteandroidapp.R
import com.ndteam.wasteandroidapp.models.GarbageCategory
import com.ndteam.wasteandroidapp.models.GarbageItem
import com.ndteam.wasteandroidapp.models.RecycleType
import com.ndteam.wasteandroidapp.models.states.GarbageItemState
import com.ndteam.wasteandroidapp.ui.theme.DividerColor
import com.ndteam.wasteandroidapp.ui.theme.IconsDark
import com.ndteam.wasteandroidapp.ui.theme.Inter
import com.ndteam.wasteandroidapp.ui.theme.MainOrange
import com.ndteam.wasteandroidapp.view.custom_views.CircularLoaderView
import com.ndteam.wasteandroidapp.view.main.MainViewModel

@Composable
fun GarbageTypeDetailsScreen(navController: NavController, viewModel: MainViewModel, garbageCategory: GarbageCategory) {

    LaunchedEffect(Unit, block = {
        viewModel.searchGarbage(garbageCategory.type.name)
    })

    GarbageTypeDetailsScreenContent(garbageCategory = garbageCategory, garbageItemState = viewModel.garbageItemState.value, navigate = { destination ->
        if (destination == null) {
            navController.popBackStack()
        }
    })

}

@Composable
fun GarbageTypeDetailsScreenContent(garbageCategory: GarbageCategory, garbageItemState: GarbageItemState, navigate: (String?) -> Unit) {

    val scrollState = rememberScrollState()

    Column (modifier = Modifier
        .verticalScroll(scrollState)) {
        ToolbarPlanIconAndTitle(listType = garbageCategory.importanceTitle) {
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

        DetailsTextView(
            descriptionText = garbageCategory.description,
        )


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
                    garbageCategory.categoryListTypeTitle(),
                    it
                )
            }
        }
    }

}

@Composable
private fun ToolbarPlanIconAndTitle(listType: String, backPressed: () -> Unit) {

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

        Text(
            text = listType,
            fontSize = 32.sp,
            fontFamily = Inter,
            fontWeight = FontWeight.Bold,
            color = IconsDark,
            modifier = Modifier.padding(horizontal = 15.dp)
        )

}

@Composable
private fun DetailsTextView(descriptionText: String) {

    Text(
        text = descriptionText,
        color = IconsDark,
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 1.sp,
        lineHeight = 24.sp,
        modifier = Modifier.padding(top = 10.dp, start = 16.dp, end = 16.dp)
    )
}

@Composable
fun GarbageExampleListDetailsView(type: String, garbage: List<GarbageItem>) {
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
            modifier = Modifier.weight(1f)
        )

        TextButton(onClick = {
            //TODO open search
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

            Divider(startIndent = 20.dp, thickness = 1.dp, color = DividerColor)

            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GarbageTypeDetailsScreenPreview() {
    GarbageTypeDetailsScreenContent(
        garbageCategory = GarbageCategory("Recycle", "https://imageio.forbes.com/specials-images/imageserve/623026466/0x0.jpg?format=jpg", RecycleType.RECYCLE, "Very imp", "good desk"),
    garbageItemState = GarbageItemState(garbageList = listOf(GarbageItem("", "What", "Recycle", RecycleType.RECYCLE))),
        navigate = {

        }
    )
}