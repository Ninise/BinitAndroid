package com.ndteam.wasteandroidapp.repository

import com.ndteam.wasteandroidapp.R
import com.ndteam.wasteandroidapp.api.WasteApi
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
                data = arrayListOf<String>("plastic bag", "meat", "cup", "pan", "banana")
            )
        } catch(e: Exception) {
            e.printStackTrace()
            Resource.Success(
                data = arrayListOf<String>("plastic bag", "meat", "cup", "pan", "banana")
            )
        }
    }

    override suspend fun getGarbageCategories(): Resource<List<GarbageCategory>> {
        return try {
            Resource.Success(
                data = arrayListOf(
                    GarbageCategory(
                        title = "Recycle",
                        R.drawable.recycling_card_image,
                        RecycleType.RECYCLE,
                    importanceTitle = "Why recycle is important?",
                    description = "Recycling is important because it helps to conserve natural resources, reduce waste and pollution, save energy, create jobs, and support local economies."),

                    GarbageCategory(
                        "Organic",
                        R.drawable.organic_card_image,
                        RecycleType.ORGANIC,
                        importanceTitle = "Why is important to collect organic waste separately?",
                        description = "Organic waste is composted for fertilizer, energy, or soil amendment. Composting decreases emissions, extends landfill lifespan, and enhances soil quality.\n" +
                                "Recycling organic waste supports natural systems instead of disposing it."),

                    GarbageCategory(
                        "Waste",
                        R.drawable.waste_card_image,
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
                        R.drawable.ic_recycle_maincard,
                        RecycleType.RECYCLE,
                        importanceTitle = "Why recycle is important?",
                        description = "Recycling is important because it helps to conserve natural resources, reduce waste and pollution, save energy, create jobs, and support local economies."),

                    GarbageCategory(
                        "Organic",
                        R.drawable.ic_organic_maincard,
                        RecycleType.ORGANIC,
                        importanceTitle = "Why is important to collect organic waste separately?",
                        description = "Organic waste is composted for fertilizer, energy, or soil amendment. Composting decreases emissions, extends landfill lifespan, and enhances soil quality.\n" +
                                "Recycling organic waste supports natural systems instead of disposing it."),

                    GarbageCategory(
                        "Waste",
                        R.drawable.ic_waste_maincard,
                        RecycleType.GARBAGE,
                        importanceTitle = "Why is important to reduce count of garbage?",
                        description = "Unsorted Canadian garbage goes to landfills or incinerators, where it decomposes and emits harmful gases. Incineration produces electricity but pollutes the air with carbon monoxide and dioxins, harming public health. \n" +
                                "Sorting waste reduces the amount sent to these sites, reducing emissions and protecting the environment."))
            )
        }
    }

    override suspend fun searchGarbage(query: String): Resource<List<GarbageItem>> {
        return try {
//            Resource.Success(
//                data = api.getSuggestions()
//            )
            // return mock for now
            Resource.Success(
                data = arrayListOf<GarbageItem>(
                    GarbageItem(
                        icon = "https://im.indiatimes.in/content/2021/Jul/plastic-bottle_60df027c2b119.jpg",
                        name = "Plastic bottle",
                        wayToRecycler = "Clean it and put it in recycle bin",
                        type = RecycleType.RECYCLE
                    ),
                    GarbageItem(
                        icon = "https://img.huffingtonpost.com/asset/5bad6d8b2200003501daad00.jpeg",
                        name = "Plastic bag",
                        wayToRecycler = "Put it in recycler bin",
                        type = RecycleType.ORGANIC
                    ),
                    GarbageItem(
                        icon = "https://akns-images.eonline.com/eol_images/Entire_Site/2022912/rs_1200x1200-221012142652-1200-balendciaga-lays-potato-chip-purse.jpg",
                        name = "Plastic pack",
                        wayToRecycler = "Put it in garbage bin",
                        type = RecycleType.GARBAGE
                    )
                )
            )
        } catch(e: Exception) {
            e.printStackTrace()
            Resource.Success(
                data = arrayListOf<GarbageItem>(
                    GarbageItem(
                        icon = "https://im.indiatimes.in/content/2021/Jul/plastic-bottle_60df027c2b119.jpg",
                        name = "Plastic bottle",
                        wayToRecycler = "Clean it and put it in recycle bin",
                        type = RecycleType.RECYCLE
                    ),
                    GarbageItem(
                        icon = "https://img.huffingtonpost.com/asset/5bad6d8b2200003501daad00.jpeg",
                        name = "Plastic bag",
                        wayToRecycler = "Put it in recycler bin",
                        type = RecycleType.ORGANIC
                    ),
                    GarbageItem(
                        icon = "https://akns-images.eonline.com/eol_images/Entire_Site/2022912/rs_1200x1200-221012142652-1200-balendciaga-lays-potato-chip-purse.jpg",
                        name = "Plastic pack",
                        wayToRecycler = "Put it in garbage bin",
                        type = RecycleType.GARBAGE
                    ),
                )
            )
        }
    }
}