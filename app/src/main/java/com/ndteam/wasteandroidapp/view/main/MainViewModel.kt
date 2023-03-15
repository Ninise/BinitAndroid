package com.ndteam.wasteandroidapp.view.main

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.ndteam.wasteandroidapp.R
import com.ndteam.wasteandroidapp.base.BaseViewModel
import com.ndteam.wasteandroidapp.models.GarbageCategory
import com.ndteam.wasteandroidapp.models.RecycleType
import com.ndteam.wasteandroidapp.models.states.SuggestionState
import com.ndteam.wasteandroidapp.repository.WasteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val repository: WasteRepository
): BaseViewModel() {

    private val _suggestionState = mutableStateOf(SuggestionState())
    val suggestionState: State<SuggestionState> = _suggestionState

    fun getSearchSuggestions()  {
        viewModelScope.launch {
            val result = repository.getSuggestions()
            _suggestionState.value = SuggestionState(
                suggestions = result.data,
                isLoading = false,
                error = result.message

            )
        }
    }
    fun getGarbageCategories() : List<GarbageCategory> {
       return  arrayListOf<GarbageCategory>(
            GarbageCategory("Recycle", R.drawable.ic_recycle_maincard, RecycleType.RECYCLE),
            GarbageCategory("Organic", R.drawable.ic_organic_maincard, RecycleType.ORGANIC),
            GarbageCategory("Garbage", R.drawable.ic_waste_maincard, RecycleType.GARBAGE),
        )
    }

    fun downloadData() {
        Log.d("TAG", "downloadData: ")
    }

}