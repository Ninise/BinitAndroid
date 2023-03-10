package com.ndteam.wasteandroidapp.models

import com.ndteam.wasteandroidapp.R

data class GarbageItem(
    val icon: String,
    val name: String,
    val wayToRecycler: String,
    val type: RecycleType,
) : GarbageIcon(type)

enum class RecycleType {
    RECYCLE, GARBAGE, ORGANIC
}
