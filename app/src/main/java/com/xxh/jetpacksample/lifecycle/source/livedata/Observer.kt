package com.xxh.jetpacksample.lifecycle.source.livedata

/**
 * A simple callback that can receive from [LiveData].
 *
 * @see LiveData LiveData - for a usage description.
 */
fun interface Observer<T> {

    /**
     * Called when the data is changed to [value].
     */
    fun onChanged(value: T)
}
