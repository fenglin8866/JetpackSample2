package com.xxh.jetpacksample.ioc.hilt.di

import com.xxh.jetpacksample.ioc.hilt.navigator.AppNavigator
import com.xxh.jetpacksample.ioc.hilt.navigator.AppNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class NavigationModule {
    @Binds
    abstract fun bindNavigator(impl: AppNavigatorImpl): AppNavigator
}