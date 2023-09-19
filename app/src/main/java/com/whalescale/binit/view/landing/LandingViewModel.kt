package com.whalescale.binit.view.landing

import androidx.lifecycle.viewModelScope
import com.whalescale.binit.base.BaseViewModel
import com.whalescale.binit.repository.BinitRepository
import com.whalescale.binit.utils.GameUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(val repository: BinitRepository): BaseViewModel() {

    fun downloadData() {

        viewModelScope.launch {

            val result = repository.getQuizQuestions()

            result.data?.let {
                GameUtils.saveQuizQuestions(it)
            }


        }

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