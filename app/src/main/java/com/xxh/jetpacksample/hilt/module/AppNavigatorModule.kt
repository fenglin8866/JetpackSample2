package com.xxh.jetpacksample.hilt.module

import com.xxh.jetpacksample.hilt.navigator.AppNavigator
import com.xxh.jetpacksample.hilt.navigator.AppNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@InstallIn(ActivityComponent::class)
@Module
interface AppNavigatorModule {

    @Binds
    fun bindAppNavigator(impl: AppNavigatorImpl): AppNavigator
}