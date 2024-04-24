package com.xxh.jetpacksample.lifecycle.sourcesample


object XLifecycling {
    fun lifecycleEventObserver(`object`: Any): XLifecycleEventObserver {
        val isXDefaultLifecycleObserver = `object` is XDefaultLifecycleObserver
        val isXLifecycleEventObserver = `object` is XLifecycleEventObserver
        if (isXDefaultLifecycleObserver && isXLifecycleEventObserver) {
            return XDefaultLifecycleObserverAdapter(
                `object` as XDefaultLifecycleObserver,
                `object` as XLifecycleEventObserver
            )
        }
        if (isXDefaultLifecycleObserver) {
            return XDefaultLifecycleObserverAdapter(
                `object` as XDefaultLifecycleObserver,
                null
            )
        }
        if(isXLifecycleEventObserver){
            return  `object` as XLifecycleEventObserver
        }
        return XLifecycleEventObserver { owner, event -> TODO("Not yet implemented") }
    }
}