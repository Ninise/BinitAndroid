package com.ndteam.wasteandroidapp.repository

import com.ndteam.wasteandroidapp.api.ApiService
import com.ndteam.wasteandroidapp.models.GarbageCategory
import com.ndteam.wasteandroidapp.models.RecycleType
import com.ndteam.wasteandroidapp.models.responses.Article
import com.ndteam.wasteandroidapp.models.responses.Product
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

    suspend fun searchProducts(query: String): Resource<List<Product>> {

        return try {
            Resource.Success(
                data = apiService.searchProducts(query).data
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(
                "Failed to get the data from the server", null
            )
        }
    }

    fun getGarbageCategories(): Resource<List<GarbageCategory>> {
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
                                "Sorting waste reduces the amount sent to these sites, reducing emissions and protecting the environment."),

                    GarbageCategory(
                        "E-waste",
                        "https://imageio.forbes.com/specials-images/imageserve/623026466/0x0.jpg?format=jpg&width=1200",
                        RecycleType.E_WASTE,
                        importanceTitle = "Why is important to reduce count of garbage?",
                        description = "Unsorted Canadian garbage goes to landfills or incinerators, where it decomposes and emits harmful gases. Incineration produces electricity but pollutes the air with carbon monoxide and dioxins, harming public health. \n" +
                                "Sorting waste reduces the amount sent to these sites, reducing emissions and protecting the environment."),

                    GarbageCategory(
                        "Household hazardous",
                        "https://imageio.forbes.com/specials-images/imageserve/623026466/0x0.jpg?format=jpg&width=1200",
                        RecycleType.HAZARD,
                        importanceTitle = "Why is important to reduce count of garbage?",
                        description = "Unsorted Canadian garbage goes to landfills or incinerators, where it decomposes and emits harmful gases. Incineration produces electricity but pollutes the air with carbon monoxide and dioxins, harming public health. \n" +
                                "Sorting waste reduces the amount sent to these sites, reducing emissions and protecting the environment.")
                )
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
                                "Sorting waste reduces the amount sent to these sites, reducing emissions and protecting the environment.")
                )
            )
        }
    }


}