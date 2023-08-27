package com.ndteam.wasteandroidapp.api

import com.ndteam.wasteandroidapp.models.GarbageCategory
import com.ndteam.wasteandroidapp.models.QuizObject
import com.ndteam.wasteandroidapp.models.requests.SuggestRequest
import com.ndteam.wasteandroidapp.models.responses.Article
import com.ndteam.wasteandroidapp.models.responses.Product
import com.ndteam.wasteandroidapp.models.responses.QuickSearch
import com.ndteam.wasteandroidapp.models.responses.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


object WasteAPIKeys {
         const val GARBAGE_ITEMS = "garbage_items"
         const val ICON = "icon"
         const val NAME = "name"
         const val WAY_TO_RECYCLER = "wayToRecycler"
         const val TYPE = "type"
}

interface ApiService {

    @GET("/articles")
    suspend fun getAllArticles(): Response<List<Article>>

    @GET("/products")
    suspend fun searchProducts(@Query("query") query: String, @Query("offset") offset: Int, @Query("limit") limit: Int): Response<List<Product>>

    @GET("/quick_search")
    suspend fun getQuickSearchSuggestions(): Response<List<QuickSearch>>

    @GET("/garbage_categories")
    suspend fun getGarbageCategories(): Response<List<GarbageCategory>>

    @GET("/quiz_questions")
    suspend fun getQuizQuestions(): Response<List<QuizObject>>

    @POST("/suggested")
    suspend fun makeSuggestion(@Body request: SuggestRequest): Response<SuggestRequest>


}