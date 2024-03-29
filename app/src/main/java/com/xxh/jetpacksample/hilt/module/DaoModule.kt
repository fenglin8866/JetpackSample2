package com.xxh.jetpacksample.hilt.module

import android.content.Context
import androidx.room.Room
import com.xxh.jetpacksample.hilt.data.AppDatabase
import com.xxh.jetpacksample.hilt.data.LogDao
import com.xxh.jetpacksample.hilt.data.LoggerLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DaoModule {

    @Provides
    fun providerLogDao(database:  AppDatabase): LogDao {
        return database.logDao()
    }
    @Singleton
    @Provides
    fun getDB(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "log_db").build()
    }

}