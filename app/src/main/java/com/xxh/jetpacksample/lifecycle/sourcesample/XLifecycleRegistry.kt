package com.xxh.jetpacksample.lifecycle.sourcesample

class XLifecycleRegistry : XLifecycle() {
    override fun addObserver(observer: XLifecycleObserver) {
        TODO("Not yet implemented")
    }

    override fun removeObserver(observer: XLifecycleObserver) {
        TODO("Not yet implemented")
    }

    override val currentState: State
        get() = TODO("Not yet implemented")

    internal class ObserverWithState(observer: XLifecycleObserver?, initialState: State) {
        var state: State
        var lifecycleObserver: XLifecycleEventObserver

        init {
            lifecycleObserver = XLifecycling.lifecycleEventObserver(observer!!)
            state = initialState
        }

        fun dispatchEvent(owner: XLifecycleOwner?, event: Event) {
            val newState = event.targetState
            lifecycleObserver.onStateChanged(owner!!, event)
            state = newState
        }
    }
}