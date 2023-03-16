package com.ndteam.wasteandroidapp.view.main.screens.search

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ndteam.wasteandroidapp.R
import com.ndteam.wasteandroidapp.models.GarbageItem
import com.ndteam.wasteandroidapp.models.RecycleType
import com.ndteam.wasteandroidapp.ui.theme.*
import com.ndteam.wasteandroidapp.view.main.MainViewModel
import kotlin.math.min


@Composable
fun GarbageTypeDetailsScreen(navController: NavController, viewModel: MainViewModel, garbageType: RecycleType) {
    val scrollState = rememberScrollState()

   val garbageCategory = viewModel.getGarbageCategoryByType(type = garbageType)

    val garbage = arrayListOf(
        GarbageItem(
            icon = "https://im.indiatimes.in/content/2021/Jul/plastic-bottle_60df027c2b119.jpg",
            name = "Plastic bottle",
            wayToRecycler = "Clean it and put it in recycle bin",
            type = RecycleType.RECYCLE
        ),
        GarbageItem(
            icon = "https://img.huffingtonpost.com/asset/5bad6d8b2200003501daad00.jpeg",
            name = "Plastic bag",
            wayToRecycler = "Put it in recycler bin",
            type = RecycleType.ORGANIC
        ),
        GarbageItem(
            icon = "https://akns-images.eonline.com/eol_images/Entire_Site/2022912/rs_1200x1200-221012142652-1200-balendciaga-lays-potato-chip-purse.jpg",
            name = "Plastic pack",
            wayToRecycler = "Put it in garbage bin",
            type = RecycleType.GARBAGE
        ),
        GarbageItem(
            icon = "https://img.huffingtonpost.com/asset/5bad6d8b2200003501daad00.jpeg",
            name = "Plastic bag",
            wayToRecycler = "Put it in recycler bin",
            type = RecycleType.ORGANIC
        ),
        GarbageItem(
            icon = "https://akns-images.eonline.com/eol_images/Entire_Site/2022912/rs_1200x1200-221012142652-1200-balendciaga-lays-potato-chip-purse.jpg",
            name = "Plastic pack",
            wayToRecycler = "Put it in garbage bin",
            type = RecycleType.GARBAGE
        ),
    )


    Box {

        // the rest

        Box(modifier =
        Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(color = garbageCategory.categoryColor())) {

            DetailsHeaderView(headerImage = garbageCategory.categoryHeaderImage(), scrollStateValue = scrollState.value)

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
                    .padding(top = 147.dp)
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

                    GarbageExampleListDetailsView(garbageCategory.categoryListTypeTitle(), garbage)

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
private fun DetailsHeaderView(headerImage: Int, scrollStateValue: Int) {
    Box {

        Image(
            painter = painterResource(id = headerImage),
            contentDescription = "City background",
            modifier = Modifier
                .height(160.dp)
                .fillMaxWidth()
                .graphicsLayer {
                    alpha = min(1f, 1 - (scrollStateValue / 600f))
                    translationY = -scrollStateValue * 0.1f
                },
            contentScale = ContentScale.Crop
        )
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
    GarbageTypeDetailsScreen(navController = NavController(LocalContext.current), viewModel(), RecycleType.RECYCLE)
}