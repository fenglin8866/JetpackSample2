package com.xxh.jetpacksample.lifecycle.source

/**
 * Callback interface for listening to [LifecycleOwner] state changes.
 * If a class implements both this interface and [LifecycleEventObserver], then
 * methods of `DefaultLifecycleObserver` will be called first, and then followed by the call
 * of [LifecycleEventObserver.onStateChanged]
 *
 *
 * If a class implements this interface and in the same time uses [OnLifecycleEvent], then
 * annotations will be ignored.
 */
interface DefaultLifecycleObserver : LifecycleObserver {
    /**
     * Notifies that `ON_CREATE` event occurred.
     *
     *
     * This method will be called after the [LifecycleOwner]'s `onCreate`
     * method returns.
     *
     * @param owner the component, whose state was changed
     */
    fun onCreate(owner: LifecycleOwner) {}

    /**
     * Notifies that `ON_START` event occurred.
     *
     *
     * This method will be called after the [LifecycleOwner]'s `onStart` method returns.
     *
     * @param owner the component, whose state was changed
     */
    fun onStart(owner: LifecycleOwner) {}

    /**
     * Notifies that `ON_RESUME` event occurred.
     *
     *
     * This method will be called after the [LifecycleOwner]'s `onResume`
     * method returns.
     *
     * @param owner the component, whose state was changed
     */
    fun onResume(owner: LifecycleOwner) {}

    /**
     * Notifies that `ON_PAUSE` event occurred.
     *
     *
     * This method will be called before the [LifecycleOwner]'s `onPause` method
     * is called.
     *
     * @param owner the component, whose state was changed
     */
    fun onPause(owner: LifecycleOwner) {}

    /**
     * Notifies that `ON_STOP` event occurred.
     *
     *
     * This method will be called before the [LifecycleOwner]'s `onStop` method
     * is called.
     *
     * @param owner the component, whose state was changed
     */
    fun onStop(owner: LifecycleOwner) {}

    /**
     * Notifies that `ON_DESTROY` event occurred.
     *
     *
     * This method will be called before the [LifecycleOwner]'s `onDestroy` method
     * is called.
     *
     * @param owner the component, whose state was changed
     */
    fun onDestroy(owner: LifecycleOwner) {}
}
