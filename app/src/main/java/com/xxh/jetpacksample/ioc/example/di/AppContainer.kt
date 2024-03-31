package com.xxh.jetpacksample.ioc.example.di

import com.xxh.jetpacksample.ioc.example.di.data.LoginRetrofitService
import com.xxh.jetpacksample.ioc.example.di.data.UserLocalDataSource
import com.xxh.jetpacksample.ioc.example.di.data.UserRemoteDataSource
import com.xxh.jetpacksample.ioc.example.di.data.UserRepository

class AppContainer {
    private val userLocalDataSource = UserLocalDataSource()
    private val loginRetrofitService = LoginRetrofitService()
    private val userRemoteDataSource = UserRemoteDataSource(loginRetrofitService)
    val userRepository = UserRepository(userLocalDataSource, userRemoteDataSource)
    var loginContainer: LoginContainer? = null
    //val viewModel= LoginViewModel(userRepository)

}