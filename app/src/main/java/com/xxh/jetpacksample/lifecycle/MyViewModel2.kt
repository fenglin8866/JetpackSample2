package com.xxh.jetpacksample.lifecycle

import androidx.lifecycle.ViewModel
import com.xxh.jetpacksample.lifecycle.CloseableCoroutineScope

class MyViewModel2(
    private val coroutineScope: CloseableCoroutineScope = CloseableCoroutineScope()
) : ViewModel(coroutineScope) {
    // Other ViewModel logic ...
}