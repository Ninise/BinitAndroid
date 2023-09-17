package com.ndteam.wasteandroidapp.models.states

import com.ndteam.wasteandroidapp.models.GarbageItem

data class GarbageItemState(
    val garbageList: ArrayList<GarbageItem> = arrayListOf(),
    var isLoading: Boolean = false,
    val error: String? = null
)
