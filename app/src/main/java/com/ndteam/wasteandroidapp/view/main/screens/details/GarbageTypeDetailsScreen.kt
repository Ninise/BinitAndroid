package com.ndteam.wasteandroidapp.view.main.screens.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ndteam.wasteandroidapp.R
import com.ndteam.wasteandroidapp.models.GarbageCategory
import com.ndteam.wasteandroidapp.models.GarbageItem
import com.ndteam.wasteandroidapp.models.RecycleType
import com.ndteam.wasteandroidapp.ui.theme.*
import com.ndteam.wasteandroidapp.utils.Utils
import com.ndteam.wasteandroidapp.view.main.MainViewModel
import kotlin.math.min

@Composable
fun GarbageTypeDetailsScreen(navController: NavController, viewModel: MainViewModel, garbageCategory: GarbageCategory) {
    val scrollState = rememberScrollState()


    LaunchedEffect(Unit, block = {
        viewModel.searchGarbage(garbageCategory.type.name)
    })

    garbageCategory?.let { garbageCategory ->
        Box  {

            // the rest

            Box(modifier =
            Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(color = garbageCategory.categoryColor())) {

                DetailsHeaderView(headerImage = garbageCategory.categoryHeaderImage(), image = garbageCategory.image,  scrollStateValue = scrollState.value)

                Row (modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ToolbarPlanIconAndTitle(listType = garbageCategory.categoryListTypeTitle()) {
                        navController.popBackStack()
                    }
                }

                Box(
                    modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(top = 190.dp)
                        .background(
                            color = Color.White,
                            RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                        )) {

                    Column(modifier = Modifier.padding(start = 21.dp, end = 21.dp, top = 28.dp)) {

                        DetailsTextView(
                            title = garbageCategory.title,
                            descriptionText = garbageCategory.description,
                            typeIcon = garbageCategory.categoryIcon()
                        )


                        if (viewModel.garbageItemState.value.isLoading ?: false) {
                            Column(modifier = Modifier
                                .fillMaxWidth()
                                .height(600.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    contentAlignment = Alignment.TopCenter,
                                    modifier = Modifier
                                        .width(50.dp)
                                        .height(50.dp)
                                        .padding(top = 40.dp)

                                ) {
                                    CircularProgressIndicator(color = garbageCategory.categoryColor())

                                }
                            }
                        } else {
                            viewModel.garbageItemState.value.garbageList?.let {
                                GarbageExampleListDetailsView(
                                    garbageCategory.categoryListTypeTitle(),
                                    it
                                )
                            }
                        }

                    }


                }

            }

            if (scrollState.value > 0) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .graphicsLayer {
                            alpha = scrollState.value / 350f
                        }
                    ,
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row (modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .background(garbageCategory.categoryColor()),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ToolbarPlanIconAndTitle(listType = garbageCategory.categoryListTypeTitle()) {
                            navController.popBackStack()
                        }
                    }
                }
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
            tint = Color.White,
            contentDescription = "",
            modifier = Modifier
                .padding(start = 15.dp)
                .size(24.dp)
        )
    }

    Text(
        text = listType,
        modifier = Modifier.padding(horizontal = 16.dp),
        fontSize = 16.sp,
        fontFamily = Inter,
        fontWeight = FontWeight.Bold,
        color = Color.White
    )
}

@Composable
private fun DetailsHeaderView(headerImage: Int, image: Int, scrollStateValue: Int) {
    Box {

        Image(
            painter = painterResource(id = headerImage),
            contentDescription = "City background",
            modifier = Modifier
                .height(210.dp)
                .fillMaxWidth()
                .graphicsLayer {
                    alpha = min(1f, 1 - (scrollStateValue / 600f))
                    translationY = -scrollStateValue * 0.1f
                },
            contentScale = ContentScale.Crop
        )

        Column (modifier = Modifier
            .align(alignment = Alignment.Center)
            .fillMaxWidth()) {
            Image(
                painter = painterResource(id = image),
                contentDescription = "Waste Type image",
                modifier = Modifier
                    .height(160.dp)
                    .align(alignment = Alignment.CenterHorizontally),
                contentScale = ContentScale.Fit
            )
            
            Spacer(modifier = Modifier.height(10.dp))

        }
    }

}

@Composable
private fun DetailsTextView(title: String, descriptionText: String, typeIcon: Int) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            color = TitleText,
            fontFamily = Nunito,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            letterSpacing = 1.sp,
            modifier = Modifier.weight(1f)
        )

//        val infiniteTransition = rememberInfiniteTransition()
//        val rotateAnimation = infiniteTransition.animateFloat(
//            initialValue = 0f,
//            targetValue = 360f,
//            animationSpec = infiniteRepeatable(tween(5_000, easing = LinearEasing))
//        )

        Icon(
            painterResource(id = typeIcon),
            tint = GarbageTypeIconColor,
            contentDescription = "",
            modifier = Modifier
//                .rotate(rotateAnimation.value)
                .size(25.dp),
        )
    }

    Text(
        text = descriptionText,
        color = BodyText,
        fontFamily = Nunito,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 1.sp,
        modifier = Modifier.padding(top = 10.dp)

    )
}

@Composable
fun GarbageExampleListDetailsView(type: String, garbage: List<GarbageItem>) {
    Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.padding(top = 30.dp)) {
        Text(
            text = "Products for $type bin:",
            color = TitleText,
            fontFamily = Inter,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            letterSpacing = 1.sp,
            modifier = Modifier.weight(1f)
        )

        TextButton(onClick = {
            //TODO open search
        }) {
            Text(
                text = "See all",
                color = TitleText,
                fontFamily = Inter,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                letterSpacing = 1.sp
            )
        }

    }

    LazyColumn(modifier = Modifier.height(600.dp)) {
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
    GarbageTypeDetailsScreen(navController = NavController(LocalContext.current), viewModel(), GarbageCategory("", 0, RecycleType.RECYCLE, "", ""))
}