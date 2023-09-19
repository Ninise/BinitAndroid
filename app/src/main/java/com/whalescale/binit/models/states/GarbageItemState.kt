package com.whalescale.binit.models.states

import com.whalescale.binit.models.GarbageItem

data class GarbageItemState(
    val garbageList: ArrayList<GarbageItem> = arrayListOf(),
    var isLoading: Boolean = false,
    val error: String? = null
)
