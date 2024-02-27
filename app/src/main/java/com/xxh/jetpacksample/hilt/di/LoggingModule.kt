package com.xxh.jetpacksample.hilt.di

import com.xxh.jetpacksample.hilt.data.LoggerDataSource
import com.xxh.jetpacksample.hilt.data.LoggerInMemoryDataSource
import com.xxh.jetpacksample.hilt.data.LoggerLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class InMemoryLogger

@Qualifier
annotation class DatabaseLogger

@InstallIn(SingletonComponent::class)
@Module
abstract class LoggingDatabaseModule {
    @DatabaseLogger
    @Singleton
    @Binds
    abstract fun bindDatabaseLogger(impl: LoggerLocalDataSource): LoggerDataSource

}

@InstallIn(ActivityComponent::class)
@Module
abstract class LoggingInMemoryModule {
    @InMemoryLogger
    @ActivityScoped
    @Binds
    abstract fun bindInMemoryLogger(impl: LoggerInMemoryDataSource): LoggerDataSource

}