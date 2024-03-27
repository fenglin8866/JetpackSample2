package com.xxh.jetpacksample

import android.app.Application
import com.xxh.jetpacksample.room.codelab.data.WordRepository
import com.xxh.jetpacksample.ioc.dagger.storage.SharedPreferencesStorage
import com.xxh.jetpacksample.ioc.dagger.user.UserManager
import com.xxh.jetpacksample.room.codelab.data.database.AppDatabase
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

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

    private val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val databaseApp by lazy { AppDatabase.getDatabase(this, applicationScope) }

    val repository by lazy {
        WordRepository(databaseApp.wordDao())
    }
}