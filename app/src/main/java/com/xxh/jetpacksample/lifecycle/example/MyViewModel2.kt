package com.xxh.jetpacksample.lifecycle.example

import androidx.lifecycle.ViewModel

class MyViewModel2(
    private val coroutineScope: CloseableCoroutineScope = CloseableCoroutineScope()
) : ViewModel(coroutineScope) {
    // Other ViewModel logic ...
}