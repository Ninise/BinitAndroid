package com.ndteam.wasteandroidapp.view.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
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

        Utils.log("searchGarbage ${query}")

        if (query.isNotEmpty()) {
            viewModelScope.launch {
                _garbageItemState.value = GarbageItemState(isLoading = true)

                val result = repository.searchGarbage(query)

                Utils.log("searchGarbage 2 ${result.data?.size}")

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

}