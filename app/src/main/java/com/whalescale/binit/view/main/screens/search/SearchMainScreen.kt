package com.whalescale.binit.view.main.screens.search

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
//import com.google.android.gms.ads.AdLoader
//import com.google.android.gms.ads.nativead.NativeAd
import com.whalescale.binit.App.Companion.context
import com.whalescale.binit.R
import com.whalescale.binit.models.GarbageCategory
import com.whalescale.binit.models.GarbageItem
import com.whalescale.binit.models.states.GarbageItemState
import com.whalescale.binit.ui.theme.*
import com.whalescale.binit.utils.Const
import com.whalescale.binit.utils.Const.ELECTRONIC_WASTE_TYPE
import com.whalescale.binit.utils.Const.GARBAGE_TYPE
import com.whalescale.binit.utils.Const.HHW_TYPE
import com.whalescale.binit.utils.Const.ORGANIC_TYPE
import com.whalescale.binit.utils.Const.RECYCLE_TYPE
import com.whalescale.binit.utils.Utils
import com.whalescale.binit.view.custom_views.CircularLoaderView
import com.whalescale.binit.view.main.MainViewModel
import com.whalescale.binit.view.main.screens.schedule.ScheduleScreenContent
import java.util.*


@Composable
fun SearchScreen(query: String = "") {
    SearchMainScreen(viewModel = hiltViewModel<MainViewModel>(), query = query)
}

@Composable
fun SearchMainScreen(viewModel: MainViewModel, query: String = "") {

    val textState = remember { mutableStateOf(TextFieldValue(query)) }

    val searchSuggestions = viewModel.suggestionState.value.suggestions ?: listOf()

    LaunchedEffect(key1 = Unit, block = {
        if (query.isNotEmpty()) {
            viewModel.searchGarbage(query, limit = 25)
        } else {
            viewModel.clearSearch()
        }
    })

    SearchMainScreenContent(
        textState = textState,
        isLoading = viewModel.garbageItemState.value.isLoading,
        list = viewModel.garbageItemState.value.garbageList as List<GarbageItem>,
        searchSuggestions = searchSuggestions,
//        ads = viewModel.ads,
        popBack = {
            viewModel.clearSearch()
        },
        onTextChange = {
            viewModel.searchGarbage(textState.value.text)
        },
        load = {
            viewModel.searchGarbage(textState.value.text)
            Utils.log("load more")
        }
    )
}

@Composable
fun SearchMainScreenContent(
    textState: MutableState<TextFieldValue>,
    isLoading: Boolean,
    list: List<GarbageItem>,
    searchSuggestions: List<String>,
    popBack: () -> Unit,
    onTextChange: () -> Unit,
    load:() -> Unit) {


    Box(modifier =
    Modifier
        .fillMaxSize()
        .background(Color.White),
        contentAlignment = Alignment.Center,
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            SearchView(state = textState, isActive = true, click = {

            }, backClick = popBack, onTextChange = onTextChange)

            if (textState.value.text.isEmpty()) {
                LazyRow(modifier = Modifier.padding(start = 15.dp)) {
                    items(searchSuggestions) {
                        SearchChip(text = it, onItemClick = {
                            textState.value = TextFieldValue(it)
                            onTextChange()
                        })

                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn(modifier = Modifier.padding(horizontal = 20.dp)) {
                @Composable
                fun quickSearchItem(category: GarbageCategory) {
                    if (textState.value.text.isEmpty()) {
                        categoryPlaceholder(
                            item = category
                        ) {
                            textState.value = TextFieldValue(category.type.lowercase())
                            onTextChange()
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Divider(startIndent = 2.dp, thickness = 1.dp, color = DividerColor)

                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }

                fun defaultItems() {
                    item {
                        quickSearchItem(
                            category = GarbageCategory("Recycle", "R.drawable.ic_recycle", RECYCLE_TYPE, "", "", "", "", items = listOf())
                        )
                    }

                    item {
                        quickSearchItem(
                            category = GarbageCategory("Garbage", "R.drawable.ic_garbage", GARBAGE_TYPE, "", "", "","", items = listOf())
                        )
                    }

                    item {
                        quickSearchItem(
                            category = GarbageCategory("Organic", "R.drawable.ic_organic", ORGANIC_TYPE, "", "", "","", items = listOf())
                        )
                    }

                    item {
                        quickSearchItem(
                            category = GarbageCategory("E-Waste", "R.drawable.ic_e_waste", ELECTRONIC_WASTE_TYPE, "", "", "","", items = listOf())
                        )
                    }

                    item {
                        quickSearchItem(
                            category = GarbageCategory("Household hazardous", "R.drawable.ic_hazard", HHW_TYPE, "", "", "","", items = listOf())
                        )
                    }
                }


                if (list.isEmpty() && textState.value.text.isEmpty()) {
                    defaultItems()
                }

                if (list.isEmpty() && textState.value.text.isNotEmpty()) {
                    item {
                        Column (modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 100.dp)
                            .align(alignment = Alignment.CenterHorizontally)) {

                            Image(
                                painter = painterResource(id = R.drawable.ic_no_search_results),
                                contentDescription = "No search result",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 55.dp)
                                    .align(alignment = Alignment.CenterHorizontally),
                                contentScale = ContentScale.Fit)

                            Text(
                                text = stringResource(id = R.string.oops_no_search_result),
                                color = BodyText,
                                fontFamily = Inter,
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                letterSpacing = 1.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(top = 15.dp)
                            )

                            Spacer(modifier = Modifier.padding())
                        }
                    }
                } else {
                    items(list.size) { idx ->
                        val item = list[idx]
                        Utils.log("idx: ${idx}; list.size: ${list.size}; ${item.name}")
                        if (idx == list.size - 1 && !isLoading) {
                            load()
                        }

                        GarbageItemView(
                            item = item,
                            onItemClick = {

                            }
                        )

                        Divider(
                            startIndent = 2.dp,
                            thickness = 1.dp,
                            color = DividerColor,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )

                    }
                }

            if (isLoading) {
                item {
                    Box (
                        Modifier
                            .fillMaxWidth()
                            .height(90.dp),
                        contentAlignment = Alignment.TopCenter) {
                        CircularLoaderView(color = Const.MAIN_COLORS_ARRAY.random() )
                    }
                }
            }
            }

        }
    }

}

@Composable
fun SearchView(state: MutableState<TextFieldValue>, isMockView: Boolean = false, isActive: Boolean = false, paddingTop: Dp = 15.dp, paddingBottom: Dp = 15.dp, click: () -> Unit, backClick: () -> Unit = {}, onTextChange: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
        .clickable {
            if (isMockView) {
                click()
            }
        }
        .testTag("search_view")) {

        TextField(
            value = state.value,
            onValueChange = { value ->
                state.value = value
                onTextChange()
            },
            enabled = !isMockView,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp, top = paddingTop, bottom = paddingBottom),
            placeholder = {
                Text(stringResource(R.string.search), style = TextStyle(color = HintText, fontSize = 16.sp, fontFamily = Inter, fontWeight = FontWeight.Normal))
            },
            textStyle = TextStyle(color = BodyText, fontSize = 16.sp, fontFamily = Inter, fontWeight = FontWeight.Normal),
            leadingIcon = {
                Icon(
                    painterResource(id = R.drawable.ic_search_mag_glass),
                    tint = IconsGray,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(start = 15.dp)
                        .size(24.dp)
                )
            },
            trailingIcon = {
                if (state.value != TextFieldValue("")) {
                    IconButton(
                        onClick = {
                            state.value =
                                TextFieldValue("") // Remove text from TextField when you press the 'X' icon
                            onTextChange()
                        }
                    ) {
                        Icon(
                            Icons.Default.Close,
                            tint = IconsGray,
                            contentDescription = "",
                            modifier = Modifier
                                .padding(15.dp)
                                .size(24.dp)
                        )
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(8.dp), // The TextFiled has rounded corners top left and right by default
            colors = TextFieldDefaults.textFieldColors(
                textColor = BodyText,
                cursorColor = cursorText,
                leadingIconColor = if (isActive) IconsDark else IconsGray,
                trailingIconColor = Color.White,
                backgroundColor = SearchBackGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
    }
}

@Composable
fun SearchChip(text: String, onItemClick: (String) -> Unit) {
    Box(modifier = Modifier
        .padding(horizontal = 4.dp)
        .testTag("search_chip")
        .background(color = ChipBackGray, shape = RoundedCornerShape(6.dp))
        .clickable { onItemClick(text) }) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Icon(
                painterResource(id = R.drawable.ic_quick_search_arrow),
                contentDescription = "arrow",
                modifier = Modifier
                    .size(size = 12.dp),
                tint = BodyText
            )
            Text(
                text = text,
                color = BodyText,
                fontFamily = Inter,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                letterSpacing = 1.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

    }
}

@Composable
fun GarbageItemView(item: GarbageItem, showIcon: Boolean = true, onItemClick: (String) -> Unit) {
    var isExpanded by remember {
        mutableStateOf(false)
    }


    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 10.dp)
        .animateContentSize()
        .clickable {
            onItemClick(item.name)
        },
       verticalAlignment = Alignment.CenterVertically) {

       if (item.icon.isNotEmpty()) {
           AsyncImage(
               model = item.icon,
               contentDescription = item.name,
               contentScale = ContentScale.Crop,
               modifier = Modifier
                   .size(76.dp)
                   .clip(RoundedCornerShape(11.dp))

           )
       } else {
           Box (modifier = Modifier.size(76.dp), contentAlignment = Alignment.Center) {
               Image(
                   painter = painterResource(id = Utils.categoryBinImage(item.type)),
                   contentDescription = "Bin",
                   modifier = Modifier.size(60.dp),
                   contentScale = ContentScale.Fit)
           }
       }

       Column (
           Modifier
               .weight(1f)
               .padding(horizontal = 15.dp)) {
           Text(
               text = item.name.capitalize(Locale.CANADA),
               color = TitleText,
               fontFamily = Inter,
               fontWeight = FontWeight.Medium,
               fontSize = 14.sp
           )

           Spacer(modifier = Modifier.height(2.dp))



           Text(
               text = item.wayToRecycler.trim(),
               color = BodyText,
               fontFamily = Inter,
               fontWeight = FontWeight.Normal,
               fontSize = 12.sp,
               maxLines = if (isExpanded) 10 else 2,
               overflow = TextOverflow.Ellipsis,
               modifier = Modifier
                   .animateContentSize(
                       animationSpec = spring(
                           dampingRatio = Spring.DampingRatioLowBouncy,
                           stiffness = Spring.StiffnessLow
                       )
                   )
                   .clickable {
                       isExpanded = !isExpanded
                   }
           )
       }


       if (showIcon) {
           Icon(
               painterResource(id = Utils.getDefaultIconByType(item.type)),
               tint = GarbageTypeIconColor,
               contentDescription = "",
               modifier = Modifier
                   .padding(end = 15.dp)
                   .size(25.dp)
                   .align(Alignment.CenterVertically),
           )
       }

   }
}

@Composable
fun categoryPlaceholder(item: GarbageCategory, click: () -> Unit) {
    Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier
        .height(55.dp)
        .clickable { click() }) {
        Icon(
            painterResource(id = Utils.getDefaultIconByType(item.type)),
            tint = GarbageTypeIconColor,
            contentDescription = "",
            modifier = Modifier
                .padding(end = 15.dp)
                .size(25.dp)
                .align(Alignment.CenterVertically),
        )

        Text(
            text = item.title,
            color = BodyText,
            fontFamily = Inter,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            letterSpacing = 1.sp,
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            painterResource(id = R.drawable.ic_search_go_to),
            tint = GarbageTypeIconColor,
            contentDescription = "",
            modifier = Modifier
                .padding(end = 15.dp)
                .size(18.dp)
                .align(Alignment.CenterVertically),
        )
    }
}

//@Composable
//fun NativeAd() {
//    val nativeAdState = remember { mutableStateOf<NativeAd?>(null) }
//
//    val adLoader = remember {
//        AdLoader.Builder(context, "ad_unit_id")
//            .forNativeAd { nativeAd ->
//                nativeAdState.value = nativeAd
//            }
//            .build()
//    }
//
//
//    if (nativeAdState.value != null) {
//
//    }
//}

@Preview(showBackground = true)
@Composable
fun SearchViewPreview() {

//    SearchMainScreen(
//        navController = NavController(LocalContext.current),
//        viewModel = MainViewModel(WasteRepositoryImpl(WasteApi)),
//        query = ""
//    )


    val textState = remember { mutableStateOf(TextFieldValue("")) }
    val isLoading = remember { mutableStateOf(false) }
    val garbageState = remember { mutableStateOf(GarbageItemState(arrayListOf(), false, null)) }

//
//    SearchMainScreenContent(
//        textState,
//        isLoading = isLoading.value,
//        garbageState,
//        arrayListOf(),
////        arrayListOf(),
//        popBack = {
//
//        },
//        onTextChange = {
//
//        },
//        load = {
//
//        }
//    )
}