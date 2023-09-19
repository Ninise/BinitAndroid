package com.whalescale.binit.view.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
//import com.google.android.gms.ads.AdListener
//import com.google.android.gms.ads.AdLoader
//import com.google.android.gms.ads.AdRequest
//import com.google.android.gms.ads.LoadAdError
//import com.google.android.gms.ads.nativead.NativeAd
//import com.google.android.gms.ads.nativead.NativeAdOptions
import com.whalescale.binit.App
import com.whalescale.binit.base.BaseViewModel
import com.whalescale.binit.models.GarbageCategory
import com.whalescale.binit.models.GarbageItem
import com.whalescale.binit.models.requests.SuggestRequest
import com.whalescale.binit.models.responses.Article
import com.whalescale.binit.models.states.ArticleItemState
import com.whalescale.binit.models.states.GarbageItemState
import com.whalescale.binit.models.states.GarbageState
import com.whalescale.binit.models.states.SuggestionState
import com.whalescale.binit.repository.BinitRepository
import com.whalescale.binit.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ScreenState(
    val isLoading: Boolean = false,
    val items: List<GarbageItem> = emptyList(),
    val error: String? = null,
    val endReached: Boolean = false,
    val page: Int = 0
)

@HiltViewModel
class MainViewModel @Inject constructor(
    val repository: BinitRepository
): BaseViewModel() {

    private val _suggestionState = mutableStateOf(SuggestionState())
    val suggestionState: State<SuggestionState> = _suggestionState

    private val _garbageTypeState = mutableStateOf(GarbageState())
    val garbageState: State<GarbageState> = _garbageTypeState

    private val _garbageItemState = mutableStateOf(GarbageItemState())
    val garbageItemState: State<GarbageItemState> = _garbageItemState

    private val _articlesItemsState = mutableStateOf(ArticleItemState())
    val articleItemState: State<ArticleItemState> = _articlesItemsState

//    lateinit var adLoader: AdLoader

//    val ads: ArrayList<NativeAd> = arrayListOf()

    fun clearGarbageList() {
        _garbageItemState.value = GarbageItemState(isLoading = false)
    }

    fun downloadData() {
        getSearchSuggestions()
        getGarbageCategories()
//        downloadAds()
        downloadArticles()
    }

    fun getGarbageCategoryByType(type: String) : GarbageCategory {
        garbageState.value.garbageList?.filter {
            it.type == type
        }?.let {
            return it[0]
        }?: kotlin.run {
            return GarbageCategory(
                type,
                "",
                "TITLE",
                "AUTHOR",
                "LINK",
                "DESCRIPTION",
                "",
                items = listOf()
            )
        }
    }

    fun getArticleById(id: Int) : Article {
        articleItemState.value.articlesList?.filter {
            it.id == id
        }?.let {
            return it[0]
        }?: kotlin.run {
            return Article(0, "", "", "", "", "", "", "", listOf())
        }
    }

    var prevQuery = ""
    var prevOffset = 0
    var endHasReached: Boolean = false

    fun searchGarbage(query: String, limit: Int = 25) {

        var offset = 0

        if (prevQuery == query) {
            offset = prevOffset + 26
        } else {
            endHasReached = false
        }

        if (endHasReached) {
            return
        }

        if (query.isNotEmpty()) {
            viewModelScope.launch {
                if (offset == 0) {
                    _garbageItemState.value = GarbageItemState(isLoading = true)
                } else {
                    _garbageItemState.value.isLoading = true
                }

                val result = repository.searchProducts(query, offset = offset, limit = limit)

                result.data?.let {
                    endHasReached = it.isEmpty()

                    if (it.size < limit) {
                        endHasReached = true
                    }
                }




                prevQuery = query
                prevOffset = offset

                if (prevOffset != 0) {


                    val temp = _garbageItemState.value.garbageList

                    val newItems = result.data?.map {
                        GarbageItem(it.image, it.name, it.description, it.type)
                    } as ArrayList<GarbageItem>


                    temp.addAll(newItems)


                    _garbageItemState.value = GarbageItemState(
                        garbageList = temp,
                        isLoading = false
                    )


                } else {
                    _garbageItemState.value = GarbageItemState(
                        garbageList = result.data?.map {
                            GarbageItem(it.image, it.name, it.description, it.type)
                        } as ArrayList<GarbageItem>,
                        isLoading = false,
                        error = result.message
                    )
                }


            }
        } else {
            _garbageItemState.value = GarbageItemState(
                isLoading = false,
            )
        }

    }

    fun clearSearch() {
        _garbageItemState.value = GarbageItemState(isLoading = false)
    }

    fun makeSuggestion(name: String, type: String, description: String, location: String) {
        viewModelScope.launch {
            val request = SuggestRequest(
                name = name,
                type = type,
                description = description,
                location = location
            )
            repository.makeSuggestion(request = request)
        }
    }

    private fun getSearchSuggestions()  {
        viewModelScope.launch {

            _suggestionState.value = SuggestionState(isLoading = true)

            val result = repository.getQuickSearchSuggestions()
            _suggestionState.value = SuggestionState(
                suggestions = result.data,
                isLoading = false,
                error = result.message

            )
        }
    }

    private fun getGarbageCategories() {
        viewModelScope.launch {

            _garbageTypeState.value = GarbageState(isLoading = true)

            val result = repository.getGarbageCategories()
            _garbageTypeState.value = GarbageState(
                garbageList = result.data,
                isLoading = false,
                error = result.message

            )
        }
    }

//    private fun downloadAds() {
//
//        Utils.log("downloadAds")
//
//        adLoader = AdLoader.Builder(App.context, "ca-app-pub-3940256099942544/2247696110")
//            .forNativeAd { ad : NativeAd ->
//                // Show the ad.
//                if (adLoader.isLoading) {
//                    Utils.log("ADS LOADING")
//                    // The AdLoader is still loading ads.
//                    // Expect more adLoaded or onAdFailedToLoad callbacks.
//                } else {
//                    Utils.log("ADS LOADED 1 ${ad.body}")
//                    Utils.log("ADS LOADED 2 ${ad.icon?.uri}")
//                    Utils.log("ADS LOADED 3 ${ad.images.first().uri}")
//                    // The AdLoader has finished loading ads.
//                    ads.add(ad)
//                }
//
//
//
//            }
//            .withAdListener(object : AdListener() {
//                override fun onAdFailedToLoad(adError: LoadAdError) {
//                    // Handle the failure by logging, altering the UI, and so on.
//
//                }
//            })
//            .withNativeAdOptions(
//                NativeAdOptions.Builder()
//                    // Methods in the NativeAdOptions.Builder class can be
//                    // used here to specify individual options settings.
//                    .build())
//            .build()
//
//        adLoader.loadAds(AdRequest.Builder().build(), 3)
//    }

    private fun downloadArticles() {
        viewModelScope.launch {

            _articlesItemsState.value = ArticleItemState(isLoading = true)

            val result = repository.getArticles()

            _articlesItemsState.value = ArticleItemState(
                articlesList = result.data,
                isLoading = false,
                error = result.message
            )
        }
    }
}