package com.xxh.jetpacksample.ioc.hilt.codelab.login.di

import com.xxh.jetpacksample.ioc.hilt.codelab.login.storage.SharedPreferencesStorage
import com.xxh.jetpacksample.ioc.hilt.codelab.login.storage.Storage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface StorageModule {
    @Binds
    fun bindStorage(impl:SharedPreferencesStorage):Storage
}