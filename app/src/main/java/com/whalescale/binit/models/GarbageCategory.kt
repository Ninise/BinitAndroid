package com.whalescale.binit.models

import com.whalescale.binit.ui.theme.MainBlue
import com.whalescale.binit.ui.theme.MainGreen
import com.whalescale.binit.ui.theme.MainOrange
import com.whalescale.binit.utils.Utils

data class GarbageCategory(
    val title: String,
    val image: String,
    val type: String,
    val image_author: String,
    val image_author_url: String,
    val display_type: String,
    val description: String,
    val items: List<SubCategoryItem>
) : GarbageIcon(RecycleType.parseValue(type)) {

    fun categoryBinImage() = Utils.categoryBinImage(type)

    fun categoryColor() = when(RecycleType.parseValue(type)) {
        RecycleType.RECYCLE -> MainBlue
        RecycleType.GARBAGE -> MainOrange
        else -> MainGreen
    }

}

data class SubCategoryItem(
    val title: String,
    val data: List<String>
)