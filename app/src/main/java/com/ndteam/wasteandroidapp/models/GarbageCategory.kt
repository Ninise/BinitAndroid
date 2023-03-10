package com.ndteam.wasteandroidapp.models

import com.ndteam.wasteandroidapp.R

data class GarbageCategory(
    val title: String,
    val image: Int,
    val type: RecycleType
) : GarbageIcon(type) {
    fun returnShadow() = when(type) {
        RecycleType.RECYCLE -> R.drawable.ic_recycle_shadow_maincard
        else -> R.drawable.ic_shadow_organic_maincard
    }
}
