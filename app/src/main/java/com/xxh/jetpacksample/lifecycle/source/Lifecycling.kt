package com.xxh.jetpacksample.lifecycle.source

/**
 * Internal class to handle lifecycle conversion etc.
 */
object Lifecycling {

    //对外添加观察者
    @JvmStatic
    @Suppress("DEPRECATION")
    fun lifecycleEventObserver(`object`: Any): LifecycleEventObserver {
        val isLifecycleEventObserver = `object` is LifecycleEventObserver
        val isDefaultLifecycleObserver = `object` is DefaultLifecycleObserver
        if (isLifecycleEventObserver && isDefaultLifecycleObserver) {
            return DefaultLifecycleObserverAdapter(
                `object` as DefaultLifecycleObserver,
                `object` as LifecycleEventObserver
            )
        }
        if (isDefaultLifecycleObserver) {
            return DefaultLifecycleObserverAdapter(`object` as DefaultLifecycleObserver, null)
        }
        if (isLifecycleEventObserver) {
            return `object` as LifecycleEventObserver
        }

        return LifecycleEventObserver { source, event -> TODO("Not yet implemented") }
    }
}