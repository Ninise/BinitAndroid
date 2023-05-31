package com.ndteam.wasteandroidapp.repository

import com.ndteam.wasteandroidapp.R
import com.ndteam.wasteandroidapp.api.WasteApi
import com.ndteam.wasteandroidapp.models.Article
import com.ndteam.wasteandroidapp.models.GarbageCategory
import com.ndteam.wasteandroidapp.models.GarbageItem
import com.ndteam.wasteandroidapp.models.RecycleType
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
                data = arrayListOf<String>("plastic bag", "meat", "styrofoam", "cup", "pan", "banana")
            )
        } catch(e: Exception) {
            e.printStackTrace()
            Resource.Success(
                data = arrayListOf<String>("plastic bag", "meat", "styrofoam", "cup", "pan", "banana")
            )
        }


    }

    override suspend fun getGarbageCategories(): Resource<List<GarbageCategory>> {
        return try {
            Resource.Success(
                data = arrayListOf(
                    GarbageCategory(
                        title = "Recycle",
                        "https://nationaltoday.com/wp-content/uploads/2022/07/5-Recycle-Awareness-Week-1200x834.jpg",
                        RecycleType.RECYCLE,
                    importanceTitle = "Why recycle is important?",
                    description = "Recycling is important because it helps to conserve natural resources, reduce waste and pollution, save energy, create jobs, and support local economies."),

                    GarbageCategory(
                        "Organic",
                        "https://i.cbc.ca/1.3046582.1682601407!/fileImage/httpImage/image.jpg_gen/derivatives/16x9_940/compost.jpg",
                        RecycleType.ORGANIC,
                        importanceTitle = "Why is important to collect organic waste separately?",
                        description = "Organic waste is composted for fertilizer, energy, or soil amendment. Composting decreases emissions, extends landfill lifespan, and enhances soil quality.\n" +
                                "Recycling organic waste supports natural systems instead of disposing it."),

                    GarbageCategory(
                        "Waste",
                        "https://imageio.forbes.com/specials-images/imageserve/623026466/0x0.jpg?format=jpg&width=1200",
                        RecycleType.GARBAGE,
                        importanceTitle = "Why is important to reduce count of garbage?",
                        description = "Unsorted Canadian garbage goes to landfills or incinerators, where it decomposes and emits harmful gases. Incineration produces electricity but pollutes the air with carbon monoxide and dioxins, harming public health. \n" +
                                "Sorting waste reduces the amount sent to these sites, reducing emissions and protecting the environment."))
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Success(
                data = arrayListOf(
                    GarbageCategory(
                        title = "Recycle",
                        "https://nationaltoday.com/wp-content/uploads/2022/07/5-Recycle-Awareness-Week-1200x834.jpg",
                        RecycleType.RECYCLE,
                        importanceTitle = "Why recycle is important?",
                        description = "Recycling is important because it helps to conserve natural resources, reduce waste and pollution, save energy, create jobs, and support local economies."),

                    GarbageCategory(
                        "Organic",
                        "https://i.cbc.ca/1.3046582.1682601407!/fileImage/httpImage/image.jpg_gen/derivatives/16x9_940/compost.jpg",
                        RecycleType.ORGANIC,
                        importanceTitle = "Why is important to collect organic waste separately?",
                        description = "Organic waste is composted for fertilizer, energy, or soil amendment. Composting decreases emissions, extends landfill lifespan, and enhances soil quality.\n" +
                                "Recycling organic waste supports natural systems instead of disposing it."),

                    GarbageCategory(
                        "Waste",
                        "https://imageio.forbes.com/specials-images/imageserve/623026466/0x0.jpg?format=jpg&width=1200",
                        RecycleType.GARBAGE,
                        importanceTitle = "Why is important to reduce count of garbage?",
                        description = "Unsorted Canadian garbage goes to landfills or incinerators, where it decomposes and emits harmful gases. Incineration produces electricity but pollutes the air with carbon monoxide and dioxins, harming public health. \n" +
                                "Sorting waste reduces the amount sent to these sites, reducing emissions and protecting the environment."))
            )
        }
    }

    override suspend fun searchGarbage(query: String): Resource<List<GarbageItem>> {
        return try {

            Resource.Success(
                data = api.searchGarbageElements(query)
            )
        } catch(e: Exception) {
            e.printStackTrace()
            Resource.Error(
              "Failed to get the data from Firestore", null
            )
        }
    }

    override suspend fun getArticles(): Resource<List<Article>> {
        return try {
            Resource.Success(
                data = api.getArticles()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(
                "Failed to get the data from the server", null
            )
        }
    }
}