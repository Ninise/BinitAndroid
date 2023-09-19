package com.whalescale.binit.models.states

import com.whalescale.binit.models.GarbageCategory

data class GarbageState(
    val garbageList: List<GarbageCategory>? = null,
    var isLoading: Boolean = false,
    val error: String? = null
)