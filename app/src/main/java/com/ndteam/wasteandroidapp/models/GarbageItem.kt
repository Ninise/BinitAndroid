package com.ndteam.wasteandroidapp.models

import com.ndteam.wasteandroidapp.R

data class GarbageItem(
    val icon: String,
    val name: String,
    val wayToRecycler: String,
    val type: RecycleType,
) {
    fun returnImage() = when (type) {
        RecycleType.RECYCLE -> R.drawable.ic_recycle
        RecycleType.ORGANIC -> R.drawable.ic_organic
        else -> R.drawable.ic_garbage
    }
}

enum class RecycleType {
    RECYCLE, GARBAGE, ORGANIC
}
