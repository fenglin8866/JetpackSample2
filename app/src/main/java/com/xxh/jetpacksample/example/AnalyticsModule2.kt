package com.xxh.jetpacksample.example

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Module
@InstallIn(ActivityComponent::class)
object AnalyticsModule2 {
    @Provides
    fun provideAnalyticsService(
        @AuthInterceptorOkHttpClient okHttpClient: OkHttpClient
    ): AnalyticsService2 {
        return Retrofit.Builder()
            .baseUrl("https://example.com")
            .build()
            .create(AnalyticsService2::class.java)
    }

    @Provides
    fun provideAnalyticsService2(
        // Potential dependencies of this type
    ): AnalyticsService2 {
        return Retrofit.Builder()
            .baseUrl("https://example.com")
            .build()
            .create(AnalyticsService2::class.java)
    }
}