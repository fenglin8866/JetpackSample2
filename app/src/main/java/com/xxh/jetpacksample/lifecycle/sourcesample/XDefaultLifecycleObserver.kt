package com.xxh.jetpacksample.lifecycle.sourcesample

interface XDefaultLifecycleObserver : XLifecycleObserver {
    /**
     * Notifies that `ON_CREATE` event occurred.
     *
     *
     * This method will be called after the [XLifecycleOwner]'s `onCreate`
     * method returns.
     *
     * @param owner the component, whose state was changed
     */
    fun onCreate(owner: XLifecycleOwner) {}

    /**
     * Notifies that `ON_START` event occurred.
     *
     *
     * This method will be called after the [XLifecycleOwner]'s `onStart` method returns.
     *
     * @param owner the component, whose state was changed
     */
    fun onStart(owner: XLifecycleOwner) {}

    /**
     * Notifies that `ON_RESUME` event occurred.
     *
     *
     * This method will be called after the [XLifecycleOwner]'s `onResume`
     * method returns.
     *
     * @param owner the component, whose state was changed
     */
    fun onResume(owner: XLifecycleOwner) {}

    /**
     * Notifies that `ON_PAUSE` event occurred.
     *
     *
     * This method will be called before the [XLifecycleOwner]'s `onPause` method
     * is called.
     *
     * @param owner the component, whose state was changed
     */
    fun onPause(owner: XLifecycleOwner) {}

    /**
     * Notifies that `ON_STOP` event occurred.
     *
     *
     * This method will be called before the [XLifecycleOwner]'s `onStop` method
     * is called.
     *
     * @param owner the component, whose state was changed
     */
    fun onStop(owner: XLifecycleOwner) {}

    /**
     * Notifies that `ON_DESTROY` event occurred.
     *
     *
     * This method will be called before the [XLifecycleOwner]'s `onDestroy` method
     * is called.
     *
     * @param owner the component, whose state was changed
     */
    fun onDestroy(owner: XLifecycleOwner) {}
}