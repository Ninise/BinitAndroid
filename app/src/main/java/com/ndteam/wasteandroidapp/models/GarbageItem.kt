package com.ndteam.wasteandroidapp.models

data class GarbageItem(
    val icon: String,
    val name: String,
    val wayToRecycler: String,
    val type: RecycleType,
)

enum class RecycleType {
    RECYCLE, GARBAGE, ORGANIC
}
