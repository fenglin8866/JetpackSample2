package com.xxh.jetpacksample

import android.app.Application
import com.xxh.jetpacksample.hilt.ServiceLocator
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class JApplication : Application() {
    /*lateinit var serviceLocator: ServiceLocator

    override fun onCreate() {
        super.onCreate()
        serviceLocator = ServiceLocator(applicationContext)
    }*/
}