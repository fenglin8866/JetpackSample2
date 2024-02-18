package com.xxh.jetpacksample.example

import javax.inject.Inject

class AnalyticsServiceImpl @Inject constructor(): AnalyticsService {
    override fun analyticsMethods(): String {
      return "result"
    }
}