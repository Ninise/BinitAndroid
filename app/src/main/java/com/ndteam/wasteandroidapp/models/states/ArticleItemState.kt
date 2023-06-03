package com.ndteam.wasteandroidapp.models.states

import com.ndteam.wasteandroidapp.models.Article

class ArticleItemState(
    val articlesList: List<Article>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)