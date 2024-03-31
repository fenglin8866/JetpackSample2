package com.xxh.jetpacksample.ioc.example.hilt

import javax.inject.Inject

class AnalyticsServiceImpl @Inject constructor(): AnalyticsService {
    override fun analyticsMethods(): String {
      return "result"
    }
}