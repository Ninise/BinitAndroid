package com.ndteam.wasteandroidapp.models.states

import com.ndteam.wasteandroidapp.models.GarbageItem

data class GarbageItemState(
    val garbageList: List<GarbageItem>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
