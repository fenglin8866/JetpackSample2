package com.xxh.jetpacksample.lifecycle.source.viewmodel.savedstate

import com.xxh.jetpacksample.lifecycle.source.Lifecycle
import com.xxh.jetpacksample.lifecycle.source.LifecycleEventObserver
import com.xxh.jetpacksample.lifecycle.source.LifecycleOwner
import com.xxh.jetpacksample.lifecycle.source.savedstate.SavedStateRegistry

internal class SavedStateHandleController(
    private val key: String,
    val handle: SavedStateHandle
) : LifecycleEventObserver {

    var isAttached = false
        private set

    fun attachToLifecycle(registry: SavedStateRegistry, lifecycle: Lifecycle) {
        check(!isAttached) { "Already attached to lifecycleOwner" }
        isAttached = true
        lifecycle.addObserver(this)
        registry.registerSavedStateProvider(key, handle.savedStateProvider())
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event === Lifecycle.Event.ON_DESTROY) {
            isAttached = false
            source.lifecycle.removeObserver(this)
        }
    }
}