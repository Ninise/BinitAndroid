package com.ndteam.wasteandroidapp.repository

import com.ndteam.wasteandroidapp.api.ApiService
import com.ndteam.wasteandroidapp.models.responses.Article
import com.ndteam.wasteandroidapp.utils.Resource


class BinitRepository(private val apiService: ApiService) {
    suspend fun getArticles(): Resource<List<Article>> {

        return try {
            Resource.Success(
                data = apiService.getAllArticles()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(
                "Failed to get the data from the server", null
            )
        }
    }

}