package com.xxh.jetpacksample.ioc.example

import okhttp3.OkHttpClient
import javax.inject.Inject

class ExampleServiceImpl @Inject constructor(
    @AuthInterceptorOkHttpClient private val okHttpClient: OkHttpClient
) {

}
