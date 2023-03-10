package com.ndteam.wasteandroidapp.models

import com.ndteam.wasteandroidapp.R

open class GarbageIcon(private val type: RecycleType) {

    fun returnImage() = when (type) {
        RecycleType.RECYCLE -> R.drawable.ic_recycle
        RecycleType.ORGANIC -> R.drawable.ic_organic
        else -> R.drawable.ic_garbage
    }

}