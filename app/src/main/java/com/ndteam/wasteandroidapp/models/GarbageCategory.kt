package com.ndteam.wasteandroidapp.models

import com.ndteam.wasteandroidapp.R
import com.ndteam.wasteandroidapp.ui.theme.MainBlue
import com.ndteam.wasteandroidapp.ui.theme.MainGreen
import com.ndteam.wasteandroidapp.ui.theme.MainOrange
import com.ndteam.wasteandroidapp.utils.Const.ELECTRONIC_WASTE_TYPE
import com.ndteam.wasteandroidapp.utils.Const.GARBAGE_TYPE
import com.ndteam.wasteandroidapp.utils.Const.HHW_TYPE
import com.ndteam.wasteandroidapp.utils.Const.ORGANIC_TYPE
import com.ndteam.wasteandroidapp.utils.Const.RECYCLE_TYPE
import com.ndteam.wasteandroidapp.utils.Const.YARD_WASTE_TYPE
import com.ndteam.wasteandroidapp.utils.Utils

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