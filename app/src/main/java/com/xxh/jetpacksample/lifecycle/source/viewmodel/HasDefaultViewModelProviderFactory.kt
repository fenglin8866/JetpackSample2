package com.xxh.jetpacksample.lifecycle.source.viewmodel

/**
 * Interface that marks a [ViewModelStoreOwner] as having a default
 * [ViewModelProvider.Factory] for use with [ViewModelProvider].
 */
interface HasDefaultViewModelProviderFactory {
    /**
     * Returns the default [ViewModelProvider.Factory] that should be
     * used when no custom `Factory` is provided to the
     * [ViewModelProvider] constructors.
     */
    val defaultViewModelProviderFactory: ViewModelProvider.Factory

    /**
     * Returns the default [CreationExtras] that should be passed into
     * [ViewModelProvider.Factory.create] when no overriding
     * [CreationExtras] were passed to the [ViewModelProvider] constructors.
     */
    val defaultViewModelCreationExtras: CreationExtras
        get() = CreationExtras.Empty
}
