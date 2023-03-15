package com.ndteam.wasteandroidapp.api

import retrofit2.http.GET
import retrofit2.http.Query

interface WasteApi {

        @GET("in_progress")
        suspend fun getSuggestions(): List<String>

}