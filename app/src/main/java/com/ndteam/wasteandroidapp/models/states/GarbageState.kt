package com.ndteam.wasteandroidapp.models.states

import com.ndteam.wasteandroidapp.models.GarbageCategory

data class GarbageState(
    val garbageList: List<GarbageCategory>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)