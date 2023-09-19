package com.whalescale.binit.repository

import com.whalescale.binit.R
import com.whalescale.binit.models.GarbageCategory
import com.whalescale.binit.models.GarbageItem
import com.whalescale.binit.models.RecycleType
import com.whalescale.binit.utils.Resource

open class FakeBinitRepository : BinitRepository {
    override suspend fun getSuggestions(): Resource<List<String>> = Resource.Success(listOf("plastic bag", "meat", "cup", "pan", "banana"))
    override suspend fun getGarbageCategories(): Resource<List<GarbageCategory>>  = Resource.Success(
        arrayListOf(
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
                "Garbage",
                R.drawable.ic_main_garbage_bin,
                RecycleType.GARBAGE,
                importanceTitle = "Why is important to reduce count of garbage?",
                description = "Unsorted Canadian garbage goes to landfills or incinerators, where it decomposes and emits harmful gases. Incineration produces electricity but pollutes the air with carbon monoxide and dioxins, harming public health. \n" +
                        "Sorting waste reduces the amount sent to these sites, reducing emissions and protecting the environment."))
    )

    override suspend fun searchGarbage(query: String): Resource<List<GarbageItem>> = Resource.Success(
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
}

