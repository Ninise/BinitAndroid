package com.ndteam.wasteandroidapp.view.landing

import android.util.Log
import com.ndteam.wasteandroidapp.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(): BaseViewModel() {

    fun downloadData() {
        Log.d("TAG", "downloadData: ")
    }

}