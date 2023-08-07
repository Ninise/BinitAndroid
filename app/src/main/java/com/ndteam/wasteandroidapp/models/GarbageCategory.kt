package com.ndteam.wasteandroidapp.models

import androidx.compose.ui.platform.LocalContext
import com.ndteam.wasteandroidapp.R
import com.ndteam.wasteandroidapp.ui.theme.MainBlue
import com.ndteam.wasteandroidapp.ui.theme.MainGreen
import com.ndteam.wasteandroidapp.ui.theme.MainOrange
import com.ndteam.wasteandroidapp.utils.Utils

data class GarbageCategory(
    val title: String,
    val image: String,
    val type: RecycleType,
    val importanceTitle: String,
    val description: String,
    val items: List<SubCategoryItem>
) : GarbageIcon(type) {
    fun returnShadow() = when(type) {
        RecycleType.RECYCLE -> R.drawable.recycle_card_back_gradient
        RecycleType.GARBAGE -> R.drawable.waste_card_back_gradient
        else -> R.drawable.organic_card_back_gradient
    }

    fun categoryBinImage() = when(type) {
        RecycleType.RECYCLE -> R.drawable.ic_main_recycle_bin
        RecycleType.GARBAGE -> R.drawable.ic_main_garbage_bin
        RecycleType.ORGANIC -> R.drawable.ic_main_organic_bin
        RecycleType.E_WASTE -> R.drawable.ic_main_e_waste_bin
        RecycleType.HAZARD -> R.drawable.ic_main_hazar_bing
    }

    fun categoryColor() = when(type) {
        RecycleType.RECYCLE -> MainBlue
        RecycleType.GARBAGE -> MainOrange
        else -> MainGreen
    }

    fun categoryIcon() = when(type) {
        RecycleType.RECYCLE -> R.drawable.ic_recycle
        RecycleType.GARBAGE -> R.drawable.ic_garbage
        else -> R.drawable.ic_organic
    }

    fun categoryListTypeTitle() = when(type) {
        RecycleType.RECYCLE -> Utils.string(R.string.title_recycling)
        RecycleType.GARBAGE -> Utils.string(R.string.title_garbage)
        else -> Utils.string(R.string.title_organic)
    }
}

data class SubCategoryItem(
    val title: String,
    val data: List<String>
)