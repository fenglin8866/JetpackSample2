package com.xxh.jetpacksample.lifecycle.example.test

import androidx.lifecycle.ViewModel

class MyViewModel2(
    private val coroutineScope: CloseableCoroutineScope = CloseableCoroutineScope()
) : ViewModel(coroutineScope) {
    // Other ViewModel logic ...
}