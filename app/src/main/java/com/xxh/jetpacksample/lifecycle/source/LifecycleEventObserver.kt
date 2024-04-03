package com.xxh.jetpacksample.lifecycle.source

/**
 * Class that can receive any lifecycle change and dispatch it to the receiver.
 *
 * If a class implements both this interface and
 * [androidx.lifecycle.DefaultLifecycleObserver], then
 * methods of `DefaultLifecycleObserver` will be called first, and then followed by the call
 * of [LifecycleEventObserver.onStateChanged]
 *
 * If a class implements this interface and in the same time uses [OnLifecycleEvent], then
 * annotations will be ignored.
 */
fun interface LifecycleEventObserver : LifecycleObserver {
    /**
     * Called when a state transition event happens.
     *
     * @param source The source of the event
     * @param event The event
     */
    fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event)
}