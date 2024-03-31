package com.xxh.jetpacksample.ioc.example.di

interface Factory<T> {
    fun create(): T
}