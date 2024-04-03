package com.xxh.jetpacksample.lifecycle.source.viewmodel

/**
 * A scope that owns [ViewModelStore].
 *
 * A responsibility of an implementation of this interface is to retain owned ViewModelStore
 * during the configuration changes and call [ViewModelStore.clear], when this scope is
 * going to be destroyed.
 *
 * @see ViewTreeViewModelStoreOwner
 */
interface ViewModelStoreOwner {

    /**
     * The owned [ViewModelStore]
     */
    val viewModelStore: ViewModelStore
}