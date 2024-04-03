package com.xxh.jetpacksample.lifecycle.source.viewmodel

import androidx.annotation.RestrictTo

/**
 * Class to store `ViewModel`s.
 *
 * An instance of `ViewModelStore` must be retained through configuration changes:
 * if an owner of this `ViewModelStore` is destroyed and recreated due to configuration
 * changes, new instance of an owner should still have the same old instance of
 * `ViewModelStore`.
 *
 * If an owner of this `ViewModelStore` is destroyed and is not going to be recreated,
 * then it should call [clear] on this `ViewModelStore`, so `ViewModel`s would
 * be notified that they are no longer used.
 *
 * Use [ViewModelStoreOwner.getViewModelStore] to retrieve a `ViewModelStore` for
 * activities and fragments.
 */
open class ViewModelStore {

    private val map = mutableMapOf<String, ViewModel>()

    /**
     */
    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    fun put(key: String, viewModel: ViewModel) {
        val oldViewModel = map.put(key, viewModel)
        oldViewModel?.onCleared()
    }

    /**
     * Returns the `ViewModel` mapped to the given `key` or null if none exists.
     */
    /**
     */
    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    operator fun get(key: String): ViewModel? {
        return map[key]
    }

    /**
     */
    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    fun keys(): Set<String> {
        return HashSet(map.keys)
    }

    /**
     * Clears internal storage and notifies `ViewModel`s that they are no longer used.
     */
    fun clear() {
        for (vm in map.values) {
            vm.clear()
        }
        map.clear()
    }
}