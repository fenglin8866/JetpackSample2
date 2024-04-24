package com.xxh.jetpacksample.lifecycle.sourcesample

class XDefaultLifecycleObserverAdapter(
    private val defaultLifecycleObserver: XDefaultLifecycleObserver,
    private val eventObserver: XLifecycleEventObserver?
) : XLifecycleEventObserver {
    override fun onStateChanged(owner: XLifecycleOwner, event: XLifecycle.Event) {
        when (event) {
            XLifecycle.Event.ON_CREATE -> defaultLifecycleObserver.onCreate(owner)
            XLifecycle.Event.ON_START -> defaultLifecycleObserver.onStart(owner)
            XLifecycle.Event.ON_RESUME -> defaultLifecycleObserver.onResume(owner)
            XLifecycle.Event.ON_PAUSE -> defaultLifecycleObserver.onPause(owner)
            XLifecycle.Event.ON_STOP -> defaultLifecycleObserver.onStop(owner)
            XLifecycle.Event.ON_DESTROY -> defaultLifecycleObserver.onDestroy(owner)
            XLifecycle.Event.ON_ANY -> {
            }
        }
        eventObserver?.onStateChanged(owner, event)
    }
}