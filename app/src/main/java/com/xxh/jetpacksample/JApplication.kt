package com.xxh.jetpacksample

import android.app.Application
import com.xxh.jetpacksample.dagger.storage.SharedPreferencesStorage
import com.xxh.jetpacksample.dagger.user.UserManager
import com.xxh.jetpacksample.hilt.ServiceLocator
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class JApplication : Application() {
    /*lateinit var serviceLocator: ServiceLocator

    override fun onCreate() {
        super.onCreate()
        serviceLocator = ServiceLocator(applicationContext)
    }*/

    open val userManager by lazy {
        UserManager(SharedPreferencesStorage(this))
    }
}