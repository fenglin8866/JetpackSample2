package com.xxh.jetpacksample.lifecycle.source.viewmodel.savedstate

import android.os.Bundle
import com.xxh.jetpacksample.lifecycle.source.Lifecycle
import com.xxh.jetpacksample.lifecycle.source.LifecycleEventObserver
import com.xxh.jetpacksample.lifecycle.source.LifecycleOwner
import com.xxh.jetpacksample.lifecycle.source.savedstate.SavedStateRegistry
import com.xxh.jetpacksample.lifecycle.source.savedstate.SavedStateRegistryOwner
import com.xxh.jetpacksample.lifecycle.source.viewmodel.ViewModel
import com.xxh.jetpacksample.lifecycle.source.viewmodel.ViewModelStoreOwner


internal object LegacySavedStateHandleController {
    const val TAG_SAVED_STATE_HANDLE_CONTROLLER = "androidx.lifecycle.savedstate.vm.tag"

    @JvmStatic
    fun create(
        registry: SavedStateRegistry,
        lifecycle: Lifecycle,
        key: String?,
        defaultArgs: Bundle?
    ): SavedStateHandleController {
        val restoredState = registry.consumeRestoredStateForKey(key!!)
        val handle = SavedStateHandle.createHandle(restoredState, defaultArgs)
        val controller = SavedStateHandleController(key, handle)
        controller.attachToLifecycle(registry, lifecycle)
        tryToAddRecreator(registry, lifecycle)
        return controller
    }

    @JvmStatic
    fun attachHandleIfNeeded(
        viewModel: ViewModel,
        registry: SavedStateRegistry,
        lifecycle: Lifecycle
    ) {
        val controller = viewModel.getTag<SavedStateHandleController>(
            TAG_SAVED_STATE_HANDLE_CONTROLLER
        )
        if (controller != null && !controller.isAttached) {
            controller.attachToLifecycle(registry, lifecycle)
            tryToAddRecreator(registry, lifecycle)
        }
    }

    private fun tryToAddRecreator(registry: SavedStateRegistry, lifecycle: Lifecycle) {
        val currentState = lifecycle.currentState
        if (currentState === Lifecycle.State.INITIALIZED ||
            currentState.isAtLeast(Lifecycle.State.STARTED)) {
            registry.runOnNextRecreation(OnRecreation::class.java)
        } else {
            lifecycle.addObserver(object : LifecycleEventObserver {
                override fun onStateChanged(
                    source: LifecycleOwner,
                    event: Lifecycle.Event
                ) {
                    if (event === Lifecycle.Event.ON_START) {
                        lifecycle.removeObserver(this)
                        registry.runOnNextRecreation(OnRecreation::class.java)
                    }
                }
            })
        }
    }

    internal class OnRecreation : SavedStateRegistry.AutoRecreated {
        override fun onRecreated(owner: SavedStateRegistryOwner) {
            check(owner is ViewModelStoreOwner) {
                ("Internal error: OnRecreation should be registered only on components " +
                        "that implement ViewModelStoreOwner")
            }
            val viewModelStore = (owner as ViewModelStoreOwner).viewModelStore
            val savedStateRegistry = owner.savedStateRegistry
            for (key in viewModelStore.keys()) {
                val viewModel = viewModelStore[key]
                attachHandleIfNeeded(viewModel!!, savedStateRegistry, owner.lifecycle)
            }
            if (viewModelStore.keys().isNotEmpty()) {
                savedStateRegistry.runOnNextRecreation(OnRecreation::class.java)
            }
        }
    }
}