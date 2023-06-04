package com.ndteam.wasteandroidapp.models

import com.ndteam.wasteandroidapp.R

open class GarbageIcon(private val type: RecycleType) {

    fun returnImage() = when (type) {
        RecycleType.RECYCLE -> R.drawable.ic_recycle
        RecycleType.ORGANIC -> R.drawable.ic_organic
        RecycleType.E_WASTE -> R.drawable.ic_e_waste
        RecycleType.HAZARD -> R.drawable.ic_hazard
        else -> R.drawable.ic_garbage
    }

}