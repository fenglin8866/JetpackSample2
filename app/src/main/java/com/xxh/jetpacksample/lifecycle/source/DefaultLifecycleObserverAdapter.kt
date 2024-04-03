package com.xxh.jetpacksample.lifecycle.source

/**
 * DefaultLifecycleObserver适配器
 *
 * DefaultLifecycleObserver和LifecycleEventObserver转换为单一的LifecycleEventObserver对象。
 * 方便Lifecycle内部统一控制调用
 */
internal class DefaultLifecycleObserverAdapter(
    private val defaultLifecycleObserver: DefaultLifecycleObserver,
    private val lifecycleEventObserver: LifecycleEventObserver?
) : LifecycleEventObserver {
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_CREATE -> defaultLifecycleObserver.onCreate(source)
            Lifecycle.Event.ON_START -> defaultLifecycleObserver.onStart(source)
            Lifecycle.Event.ON_RESUME -> defaultLifecycleObserver.onResume(source)
            Lifecycle.Event.ON_PAUSE -> defaultLifecycleObserver.onPause(source)
            Lifecycle.Event.ON_STOP -> defaultLifecycleObserver.onStop(source)
            Lifecycle.Event.ON_DESTROY -> defaultLifecycleObserver.onDestroy(source)
            Lifecycle.Event.ON_ANY ->
                throw IllegalArgumentException("ON_ANY must not been send by anybody")
        }
        lifecycleEventObserver?.onStateChanged(source, event)
    }
}
