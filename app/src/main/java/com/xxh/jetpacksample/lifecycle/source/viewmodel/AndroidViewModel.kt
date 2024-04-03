package com.xxh.jetpacksample.lifecycle.source.viewmodel

import android.app.Application

/**
 * Application context aware [ViewModel].
 *
 * Subclasses must have a constructor which accepts [Application] as the only parameter.
 */
open class AndroidViewModel(private val application: Application) : ViewModel() {

    /**
     * Return the application.
     */
    @Suppress("UNCHECKED_CAST")
    open fun <T : Application> getApplication(): T {
        return application as T
    }
}
