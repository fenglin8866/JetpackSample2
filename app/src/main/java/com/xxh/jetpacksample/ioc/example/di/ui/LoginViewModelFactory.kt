package com.xxh.jetpacksample.ioc.example.di.ui

import com.xxh.jetpacksample.ioc.example.di.Factory
import com.xxh.jetpacksample.ioc.example.di.data.UserRepository

class LoginViewModelFactory(private val repository: UserRepository) : Factory<LoginViewModel> {
    override fun create(): LoginViewModel {
        return LoginViewModel(repository)
    }
}