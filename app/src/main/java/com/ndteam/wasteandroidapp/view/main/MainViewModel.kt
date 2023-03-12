package com.ndteam.wasteandroidapp.view.main

import android.util.Log
import com.ndteam.wasteandroidapp.R
import com.ndteam.wasteandroidapp.base.BaseViewModel
import com.ndteam.wasteandroidapp.models.GarbageCategory
import com.ndteam.wasteandroidapp.models.RecycleType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): BaseViewModel() {


    fun getSearchSuggestions() : List<String> {
        return arrayListOf<String>("plastic bag", "meat", "cup", "pan", "banana")
    }
    fun getGarbageCategories() : List<GarbageCategory> {
       return  arrayListOf<GarbageCategory>(
            GarbageCategory("Recycle", R.drawable.ic_recycle_maincard, RecycleType.RECYCLE),
            GarbageCategory("Organic", R.drawable.ic_organic_maincard, RecycleType.ORGANIC),
            GarbageCategory("Garbage", R.drawable.ic_recycle_maincard, RecycleType.GARBAGE),
        )
    }

    fun downloadData() {
        Log.d("TAG", "downloadData: ")
    }

}