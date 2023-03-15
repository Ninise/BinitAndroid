package com.ndteam.wasteandroidapp.repository

import com.ndteam.wasteandroidapp.utils.Resource


interface WasteRepository {
    suspend fun getSuggestions(): Resource<List<String>>
}