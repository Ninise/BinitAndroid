package com.ndteam.wasteandroidapp.repository

import com.ndteam.wasteandroidapp.api.WasteApi
import com.ndteam.wasteandroidapp.utils.Resource
import javax.inject.Inject


class WasteRepositoryImpl @Inject constructor(
    private val api: WasteApi
): WasteRepository {

    override suspend fun getSuggestions(): Resource<List<String>> {
        return try {
//            Resource.Success(
//                data = api.getSuggestions()
//            )
            // return mock for now
            Resource.Success(
                data = arrayListOf<String>("plastic bag", "meat", "cup", "pan", "banana")
            )
        } catch(e: Exception) {
            e.printStackTrace()
            Resource.Success(
                data = arrayListOf<String>("plastic bag", "meat", "cup", "pan", "banana")
            )
        }
    }
}