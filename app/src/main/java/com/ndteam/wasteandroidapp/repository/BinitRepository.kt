package com.ndteam.wasteandroidapp.repository

import com.ndteam.wasteandroidapp.api.ApiService
import com.ndteam.wasteandroidapp.models.GarbageCategory
import com.ndteam.wasteandroidapp.models.RecycleType
import com.ndteam.wasteandroidapp.models.SubCategoryItem
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
                data = apiService.getGarbageCategories().data
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(
                "Failed to get the data from the server", null
            )
        }
    }

//    fun getGarbageCategories(): Resource<List<GarbageCategory>> {
//        return try {
//            Resource.Success(
//                data = arrayListOf(
//                    GarbageCategory(
//                        title = "Recycle",
//                        "https://api.binit.pro/assets/recycler_article_1.jpeg",
//                        RecycleType.RECYCLE,
//                        importanceTitle = "Why recycle is important?",
//                        description = "Recycling is our future and here's why:\n\nIt =cuts down on landfill waste=. When we recycle materials like paper, plastic, glass, and metal, we save precious natural resources. For instance, recycling one ton of paper saves 17 trees, 7,000 gallons of water, and 463 gallons of oil. That's a lot!\n\nRecycling =needs less energy than making things from scratch.= Recycling aluminum saves a whopping 95% of the energy used for production. And recycling steel saves about 60% of the energy needed for new steel. That's a win-win!\n\nRecycling =creates jobs= in collection, sorting, processing, and manufacturing. So, it's not only good for the planet, but it also helps the local economy. \n\n**But hey, let's not forget to recycle right!**\nPutting the wrong stuff in the recycle bin can cause equipment damage, hurt workers at recycling facilities, and ruin perfectly good recyclables. Cities lose millions each year due to contaminated recycling. About one-third of the items in the Blue Bin don't belong there or get ruined because of wrong items.",
//                        items = listOf(
//                            SubCategoryItem(
//                                title = "How to sort it right?",
//                                data = listOf(
//                                    "Avoid bagging items unless necessary. If you throw it in garbage chute use clear plastic bags;",
//                                    "Prepare containers by emptying, rinsing, and placing lids (including sprayers and pumps);",
//                                    "Throw black and compostable plastic in the garbage bin;",
//                                    "Dispose of flexible multi-layered packaging and plastic-lined paper in the garbage. Check for plastic lining by tearing slowly;",
//                                    "Recycle plastic bags/over-wrap separately from newspapers, flyers, magazines, and drink cases;",
//                                    "Put small pieces of paper in an envelope or bag;",
//                                    "Place foam pieces smaller than 10cm;",
//                                    "Plastic-lined coffee cups are not recyclable, put it in the garbage bin;",
//                                    "Thermal paper receipts are not recyclable because they are coated in BPA - a chemical used to produce some plastics and resins"
//                                )
//                            )
//                        )
//                    ),
//
//                    GarbageCategory(
//                        "Organic",
//                        "https://i.cbc.ca/1.3046582.1682601407!/fileImage/httpImage/image.jpg_gen/derivatives/16x9_940/compost.jpg",
//                        RecycleType.ORGANIC,
//                        importanceTitle = "Why is important to collect organic waste separately?",
//                        description = "Organic waste is composted for fertilizer, energy, or soil amendment. Composting decreases emissions, extends landfill lifespan, and enhances soil quality.\n" +
//                                "Recycling organic waste supports natural systems instead of disposing it." ,   items = listOf(
//                            SubCategoryItem(
//                                title = "How to sort it right?",
//                                data = listOf(
//                                    "Avoid bagging items unless necessary. If you throw it in garbage chute use clear plastic bags;",
//                                    "Prepare containers by emptying, rinsing, and placing lids (including sprayers and pumps);",
//                                    "Throw black and compostable plastic in the garbage bin;",
//                                    "Dispose of flexible multi-layered packaging and plastic-lined paper in the garbage. Check for plastic lining by tearing slowly;",
//                                    "Recycle plastic bags/over-wrap separately from newspapers, flyers, magazines, and drink cases;",
//                                    "Put small pieces of paper in an envelope or bag;",
//                                    "Place foam pieces smaller than 10cm;",
//                                    "Plastic-lined coffee cups are not recyclable, put it in the garbage bin;",
//                                    "Thermal paper receipts are not recyclable because they are coated in BPA - a chemical used to produce some plastics and resins"
//                                )
//                            )
//                        )),
//
//                    GarbageCategory(
//                        "Waste",
//                        "https://imageio.forbes.com/specials-images/imageserve/623026466/0x0.jpg?format=jpg&width=1200",
//                        RecycleType.GARBAGE,
//                        importanceTitle = "Why is important to reduce count of garbage?",
//                        description = "Unsorted Canadian garbage goes to landfills or incinerators, where it decomposes and emits harmful gases. Incineration produces electricity but pollutes the air with carbon monoxide and dioxins, harming public health. \n" +
//                                "Sorting waste reduces the amount sent to these sites, reducing emissions and protecting the environment." ,   items = listOf(
//                            SubCategoryItem(
//                                title = "How to sort it right?",
//                                data = listOf(
//                                    "Avoid bagging items unless necessary. If you throw it in garbage chute use clear plastic bags;",
//                                    "Prepare containers by emptying, rinsing, and placing lids (including sprayers and pumps);",
//                                    "Throw black and compostable plastic in the garbage bin;",
//                                    "Dispose of flexible multi-layered packaging and plastic-lined paper in the garbage. Check for plastic lining by tearing slowly;",
//                                    "Recycle plastic bags/over-wrap separately from newspapers, flyers, magazines, and drink cases;",
//                                    "Put small pieces of paper in an envelope or bag;",
//                                    "Place foam pieces smaller than 10cm;",
//                                    "Plastic-lined coffee cups are not recyclable, put it in the garbage bin;",
//                                    "Thermal paper receipts are not recyclable because they are coated in BPA - a chemical used to produce some plastics and resins"
//                                )
//                            )
//                        )),
//
//                    GarbageCategory(
//                        "E-waste",
//                        "https://imageio.forbes.com/specials-images/imageserve/623026466/0x0.jpg?format=jpg&width=1200",
//                        RecycleType.E_WASTE,
//                        importanceTitle = "Why is important to reduce count of garbage?",
//                        description = "Unsorted Canadian garbage goes to landfills or incinerators, where it decomposes and emits harmful gases. Incineration produces electricity but pollutes the air with carbon monoxide and dioxins, harming public health. \n" +
//                                "Sorting waste reduces the amount sent to these sites, reducing emissions and protecting the environment." ,   items = listOf(
//                            SubCategoryItem(
//                                title = "How to sort it right?",
//                                data = listOf(
//                                    "Avoid bagging items unless necessary. If you throw it in garbage chute use clear plastic bags;",
//                                    "Prepare containers by emptying, rinsing, and placing lids (including sprayers and pumps);",
//                                    "Throw black and compostable plastic in the garbage bin;",
//                                    "Dispose of flexible multi-layered packaging and plastic-lined paper in the garbage. Check for plastic lining by tearing slowly;",
//                                    "Recycle plastic bags/over-wrap separately from newspapers, flyers, magazines, and drink cases;",
//                                    "Put small pieces of paper in an envelope or bag;",
//                                    "Place foam pieces smaller than 10cm;",
//                                    "Plastic-lined coffee cups are not recyclable, put it in the garbage bin;",
//                                    "Thermal paper receipts are not recyclable because they are coated in BPA - a chemical used to produce some plastics and resins"
//                                )
//                            )
//                        )),
//
//                    GarbageCategory(
//                        "Household hazardous",
//                        "https://imageio.forbes.com/specials-images/imageserve/623026466/0x0.jpg?format=jpg&width=1200",
//                        RecycleType.HAZARD,
//                        importanceTitle = "Why is important to reduce count of garbage?",
//                        description = "Unsorted Canadian garbage goes to landfills or incinerators, where it decomposes and emits harmful gases. Incineration produces electricity but pollutes the air with carbon monoxide and dioxins, harming public health. \n" +
//                                "Sorting waste reduces the amount sent to these sites, reducing emissions and protecting the environment.",   items = listOf(
//                            SubCategoryItem(
//                                title = "How to sort it right?",
//                                data = listOf(
//                                    "Avoid bagging items unless necessary. If you throw it in garbage chute use clear plastic bags;",
//                                    "Prepare containers by emptying, rinsing, and placing lids (including sprayers and pumps);",
//                                    "Throw black and compostable plastic in the garbage bin;",
//                                    "Dispose of flexible multi-layered packaging and plastic-lined paper in the garbage. Check for plastic lining by tearing slowly;",
//                                    "Recycle plastic bags/over-wrap separately from newspapers, flyers, magazines, and drink cases;",
//                                    "Put small pieces of paper in an envelope or bag;",
//                                    "Place foam pieces smaller than 10cm;",
//                                    "Plastic-lined coffee cups are not recyclable, put it in the garbage bin;",
//                                    "Thermal paper receipts are not recyclable because they are coated in BPA - a chemical used to produce some plastics and resins"
//                                )
//                            )
//                        ))
//                )
//            )
//        } catch (e: Exception) {
//            e.printStackTrace()
//            Resource.Success(
//                data = listOf()
//            )
//        }
//    }


}