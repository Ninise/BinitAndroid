package com.whalescale.binit.models.states

import com.whalescale.binit.models.responses.Article

class ArticleItemState(
    val articlesList: List<Article>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)