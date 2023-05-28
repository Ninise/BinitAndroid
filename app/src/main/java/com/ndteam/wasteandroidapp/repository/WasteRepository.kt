package com.ndteam.wasteandroidapp.repository

import com.ndteam.wasteandroidapp.models.Article
import com.ndteam.wasteandroidapp.models.GarbageCategory
import com.ndteam.wasteandroidapp.models.GarbageItem
import com.ndteam.wasteandroidapp.utils.Resource


interface WasteRepository {
    suspend fun getSuggestions(): Resource<List<String>>

    suspend fun getGarbageCategories(): Resource<List<GarbageCategory>>

    suspend fun searchGarbage(query: String): Resource<List<GarbageItem>>

    suspend fun getArticles(): Resource<List<Article>>
}