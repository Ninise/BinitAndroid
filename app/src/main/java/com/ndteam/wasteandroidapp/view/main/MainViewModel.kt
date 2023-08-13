package com.ndteam.wasteandroidapp.view.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.ndteam.wasteandroidapp.App
import com.ndteam.wasteandroidapp.base.BaseViewModel
import com.ndteam.wasteandroidapp.models.GarbageCategory
import com.ndteam.wasteandroidapp.models.GarbageItem
import com.ndteam.wasteandroidapp.models.RecycleType
import com.ndteam.wasteandroidapp.models.responses.Article
import com.ndteam.wasteandroidapp.models.states.ArticleItemState
import com.ndteam.wasteandroidapp.models.states.GarbageItemState
import com.ndteam.wasteandroidapp.models.states.GarbageState
import com.ndteam.wasteandroidapp.models.states.SuggestionState
import com.ndteam.wasteandroidapp.repository.BinitRepository
import com.ndteam.wasteandroidapp.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

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

    lateinit var adLoader: AdLoader

    val ads: ArrayList<NativeAd> = arrayListOf()

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

    fun searchGarbage(query: String, limit: Int = 5) {

        if (query.isNotEmpty()) {
            viewModelScope.launch {
                _garbageItemState.value = GarbageItemState(isLoading = true)

                val result = repository.searchProducts(query, offset = 0, limit = limit)

                _garbageItemState.value = GarbageItemState(
                    garbageList = result.data?.map {
                                                  GarbageItem(it.image, it.name, it.description, it.type)
                    },
                    isLoading = false,
                    error = result.message
                )
            }
        } else {
            _garbageItemState.value = GarbageItemState(
                isLoading = false,
            )
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

    private fun downloadAds() {

        Utils.log("downloadAds")

        adLoader = AdLoader.Builder(App.context, "ca-app-pub-3940256099942544/2247696110")
            .forNativeAd { ad : NativeAd ->
                // Show the ad.
                if (adLoader.isLoading) {
                    Utils.log("ADS LOADING")
                    // The AdLoader is still loading ads.
                    // Expect more adLoaded or onAdFailedToLoad callbacks.
                } else {
                    Utils.log("ADS LOADED 1 ${ad.body}")
                    Utils.log("ADS LOADED 2 ${ad.icon?.uri}")
                    Utils.log("ADS LOADED 3 ${ad.images.first().uri}")
                    // The AdLoader has finished loading ads.
                    ads.add(ad)
                }



            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    // Handle the failure by logging, altering the UI, and so on.

                }
            })
            .withNativeAdOptions(
                NativeAdOptions.Builder()
                    // Methods in the NativeAdOptions.Builder class can be
                    // used here to specify individual options settings.
                    .build())
            .build()

        adLoader.loadAds(AdRequest.Builder().build(), 3)
    }

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