package com.xxh.jetpacksample.ioc.example.di

import com.xxh.jetpacksample.ioc.example.di.data.LoginUserData
import com.xxh.jetpacksample.ioc.example.di.data.UserRepository
import com.xxh.jetpacksample.ioc.example.di.ui.LoginViewModelFactory

class LoginContainer (userRepository: UserRepository){
    val loginUserData=LoginUserData()
    //共用LoginViewModel
    val loginViewModelFactory = LoginViewModelFactory(userRepository)
}