package com.whalescale.binit.models.responses

import com.whalescale.binit.models.SubCategoryItem

class Article(
    val id: Int,
    val title: String,
    val image: String,
    val type: String,
    val image_author: String?,
    val short_description: String,
    val footer: String?,
    val description: String,
    val items: List<SubCategoryItem>
)