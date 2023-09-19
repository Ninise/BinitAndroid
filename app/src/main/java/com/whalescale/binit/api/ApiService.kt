package com.whalescale.binit.api

import com.whalescale.binit.models.GarbageCategory
import com.whalescale.binit.models.QuizObject
import com.whalescale.binit.models.requests.SuggestRequest
import com.whalescale.binit.models.responses.Article
import com.whalescale.binit.models.responses.Product
import com.whalescale.binit.models.responses.QuickSearch
import com.whalescale.binit.models.responses.Response
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