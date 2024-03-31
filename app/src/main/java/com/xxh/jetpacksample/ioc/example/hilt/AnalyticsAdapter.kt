package com.xxh.jetpacksample.ioc.example.hilt

import javax.inject.Inject

class AnalyticsAdapter @Inject constructor(
    private val service: AnalyticsService
){
    fun input():String{
        return "xxh"
    }

    fun analytics():String{
         return service.analyticsMethods()
    }

}
