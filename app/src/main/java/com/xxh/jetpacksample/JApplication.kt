package com.xxh.jetpacksample

import android.app.Application
import com.xxh.jetpacksample.ioc.example.di.AppContainer
import com.xxh.jetpacksample.lifecycle.example.persistence.AppExecutors
import com.xxh.jetpacksample.lifecycle.example.persistence.DataRepository
import com.xxh.jetpacksample.lifecycle.example.persistence.db.PersistenceAppDatabase
import com.xxh.jetpacksample.room.codelab.data.WordRepository
import com.xxh.jetpacksample.room.codelab.data.database.AppDatabase
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@HiltAndroidApp
class JApplication : Application() {

    val appContainer = AppContainer()

    private val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val databaseApp by lazy { AppDatabase.getDatabase(this, applicationScope) }

    val repository by lazy {
        WordRepository(databaseApp.wordDao())
    }

    private lateinit var mAppExecutors: AppExecutors
    override fun onCreate() {
        super.onCreate()
        mAppExecutors= AppExecutors()
    }

    /*private fun getDatabase(): PersistenceAppDatabase {
        return PersistenceAppDatabase.getInstance(this, mAppExecutors)
    }*/
    private val databaseApp2 by lazy { PersistenceAppDatabase.getInstance(this, mAppExecutors) }
    fun getProductRepository(): DataRepository {
        return DataRepository.getInstance(databaseApp2)
    }
}