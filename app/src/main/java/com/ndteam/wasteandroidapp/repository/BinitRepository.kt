package com.ndteam.wasteandroidapp.repository

import com.ndteam.wasteandroidapp.api.ApiService
import com.ndteam.wasteandroidapp.models.*
import com.ndteam.wasteandroidapp.models.requests.SuggestRequest
import com.ndteam.wasteandroidapp.models.responses.Article
import com.ndteam.wasteandroidapp.models.responses.Product
import com.ndteam.wasteandroidapp.utils.Resource


class BinitRepository(private val apiService: ApiService) {
    suspend fun getArticles(): Resource<List<Article>> {

        return try {
            Resource.Success(
                data = apiService.getAllArticles().data
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(
                "Failed to get the data from the server", null
            )
        }
    }

    suspend fun searchProducts(query: String, offset: Int, limit: Int): Resource<List<Product>> {

        return try {
            Resource.Success(
                data = apiService.searchProducts(query, limit = limit, offset = offset).data
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(
                "Failed to get the data from the server", null
            )
        }
    }

    suspend fun getQuickSearchSuggestions() : Resource<List<String>> {
        return try {
            Resource.Success(
                data = apiService.getQuickSearchSuggestions().data?.map {
                    it.name
                }
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(
                "Failed to get the search suggestions", null
            )
        }
    }

    suspend fun getGarbageCategories(): Resource<List<GarbageCategory>> {
        return try {
            Resource.Success(
                data = apiService.getGarbageCategories().data?.reversed()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(
                "Failed to get the data from the server", null
            )
        }
    }

    suspend fun getQuizQuestions(): Resource<List<QuizObject>> {
        return try {
            Resource.Success(
                data = apiService.getQuizQuestions().data?.reversed()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(
                "Failed to get the data from the server", null
            )
        }
    }

    suspend fun makeSuggestion(request: SuggestRequest) : Resource<SuggestRequest> {
        return try {
            Resource.Success(
                data = apiService.makeSuggestion(request = request).data
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(
                "Error while making a suggestion", null
            )
        }
    }


}