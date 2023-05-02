package com.ndteam.wasteandroidapp.view.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.ndteam.wasteandroidapp.App
import com.ndteam.wasteandroidapp.R
import com.ndteam.wasteandroidapp.api.WasteApi
import com.ndteam.wasteandroidapp.base.BaseViewModel
import com.ndteam.wasteandroidapp.models.GarbageCategory
import com.ndteam.wasteandroidapp.models.GarbageItem
import com.ndteam.wasteandroidapp.models.RecycleType
import com.ndteam.wasteandroidapp.models.states.GarbageItemState
import com.ndteam.wasteandroidapp.models.states.GarbageState
import com.ndteam.wasteandroidapp.models.states.SuggestionState
import com.ndteam.wasteandroidapp.repository.WasteRepository
import com.ndteam.wasteandroidapp.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val repository: WasteRepository
): BaseViewModel() {

    private val _suggestionState = mutableStateOf(SuggestionState())
    val suggestionState: State<SuggestionState> = _suggestionState

    private val _garbageTypeState = mutableStateOf(GarbageState())
    val garbageState: State<GarbageState> = _garbageTypeState

    private val _garbageItemState = mutableStateOf(GarbageItemState())
    val garbageItemState: State<GarbageItemState> = _garbageItemState

    lateinit var adLoader: AdLoader

    val ads: ArrayList<NativeAd> = arrayListOf()

//    init {
//        WasteApi.addGarbageElement( GarbageItem(
//            icon = "https://www.daysoftheyear.com/wp-content/uploads/banana-day1-scaled.jpg",
//            name = "Banana",
//            wayToRecycler = "Put it in organic bin",
//            type = RecycleType.ORGANIC
//        )
//        )
//
//        WasteApi.addGarbageElement( GarbageItem(
//            icon = "https://i2-prod.mirror.co.uk/incoming/article29311198.ece/ALTERNATES/s1200/0_Wales-Daily-Life-2022.jpg",
//            name = "Plastic bottle",
//            wayToRecycler = "Put it in recycler bin",
//            type = RecycleType.RECYCLE
//        )
//        )
//
//        WasteApi.addGarbageElement(
//            GarbageItem(
//                icon = "https://facts.net/wp-content/uploads/2022/06/different-types-of-meat.jpg",
//                name = "Meat",
//                wayToRecycler = "Put it in garbage bin",
//                type = RecycleType.GARBAGE
//            )
//        )
//    }

    fun downloadData() {
        getSearchSuggestions()
        getGarbageCategories()
        downloadAds()
    }

    fun getGarbageCategoryByType(type: RecycleType) : GarbageCategory {
        garbageState.value.garbageList?.filter {
            it.type == type
        }?.let {
            return it[0]
        }?: kotlin.run {
            return GarbageCategory(
                type.name,
                R.drawable.ic_recycling_details_header,
                type,
                "TITLE",
                "DESCRIPTION"
            )
        }
    }

    fun searchGarbage(query: String) {

        if (query.isNotEmpty()) {
            viewModelScope.launch {
                _garbageItemState.value = GarbageItemState(isLoading = true)

                val result = repository.searchGarbage(query)

                _garbageItemState.value = GarbageItemState(
                    garbageList = result.data,
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

            val result = repository.getSuggestions()
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
}