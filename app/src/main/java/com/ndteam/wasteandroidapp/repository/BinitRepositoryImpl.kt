package com.ndteam.wasteandroidapp.repository


//class BinitRepositoryImpl @Inject constructor(
//    private val api: ApiService
//): BinitRepository {
//    override suspend fun getArticles(): Resource<List<Article>> {
//        return try {
//            Resource.Success(
//                data = api.getAllArticles()
//            )
//        } catch (e: Exception) {
//            e.printStackTrace()
//            Resource.Error(
//                "Failed to get the data from the server", null
//            )
//        }
//    }
//}