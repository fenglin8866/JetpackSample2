package com.xxh.jetpacksample.lifecycle.sourcesample

fun interface XLifecycleEventObserver : XLifecycleObserver {
    fun onStateChanged(owner: XLifecycleOwner, event: XLifecycle.Event)
}