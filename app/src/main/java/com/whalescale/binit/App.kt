package com.whalescale.binit

import android.app.Application
import android.content.Context
//import com.google.android.gms.ads.MobileAds
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    companion object {
        lateinit var context: Context
            private set
    }

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        FirebaseApp.initializeApp(this)

        firebaseAnalytics = Firebase.analytics

//        MobileAds.initialize(
//            this
//        ) { }
    }

}