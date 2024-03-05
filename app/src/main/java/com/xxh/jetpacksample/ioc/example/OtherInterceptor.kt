package com.xxh.jetpacksample.ioc.example

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class OtherInterceptor  @Inject constructor () :Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        TODO("Not yet implemented")
    }

}
