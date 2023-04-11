package com.ndteam.wasteandroidapp.view.landing

import com.ndteam.wasteandroidapp.api.WasteApi
import com.ndteam.wasteandroidapp.base.BaseViewModel
import com.ndteam.wasteandroidapp.models.GarbageItem
import com.ndteam.wasteandroidapp.models.RecycleType
import com.ndteam.wasteandroidapp.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(): BaseViewModel() {

    fun downloadData() {

//        WasteApi.addGarbageElement( GarbageItem(
//            icon = "https://im.indiatimes.in/content/2021/Jul/plastic-bottle_60df027c2b119.jpg",
//            name = "Plastic bottle",
//            wayToRecycler = "Clean it and put it in recycle bin",
//            type = RecycleType.RECYCLE
//        )
//        )
//
//        WasteApi.addGarbageElement( GarbageItem(
//            icon = "https://img.huffingtonpost.com/asset/5bad6d8b2200003501daad00.jpeg",
//            name = "Plastic bag",
//            wayToRecycler = "Put it in recycler bin",
//            type = RecycleType.ORGANIC
//        )
//        )
//
//        WasteApi.addGarbageElement(
//            GarbageItem(
//            icon = "https://akns-images.eonline.com/eol_images/Entire_Site/2022912/rs_1200x1200-221012142652-1200-balendciaga-lays-potato-chip-purse.jpg",
//            name = "Plastic pack",
//            wayToRecycler = "Put it in garbage bin",
//            type = RecycleType.GARBAGE
//        )
//        )
//
//        WasteApi.searchGarbageElements("pack") { items ->
//            items.forEach {
//                Utils.log(it.name)
//            }
//        }
    }

}