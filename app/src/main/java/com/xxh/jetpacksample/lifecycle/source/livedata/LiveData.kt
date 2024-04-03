package com.xxh.jetpacksample.lifecycle.source.livedata

import androidx.annotation.MainThread
import com.xxh.jetpacksample.lifecycle.source.LifecycleOwner

/**
 * Adds the given [onChanged] lambda as an observer within the lifespan of the given
 * [owner] and returns a reference to observer.
 * The events are dispatched on the main thread. If LiveData already has data
 * set, it will be delivered to the onChanged.
 *
 * The observer will only receive events if the owner is in [Lifecycle.State.STARTED]
 * or [Lifecycle.State.RESUMED] state (active).
 *
 * If the owner moves to the [Lifecycle.State.DESTROYED] state, the observer will
 * automatically be removed.
 *
 * When data changes while the [owner] is not active, it will not receive any updates.
 * If it becomes active again, it will receive the last available data automatically.
 *
 * LiveData keeps a strong reference to the observer and the owner as long as the
 * given LifecycleOwner is not destroyed. When it is destroyed, LiveData removes references to
 * the observer and the owner.
 *
 * If the given owner is already in [Lifecycle.State.DESTROYED] state, LiveData
 * ignores the call.
 */
@Deprecated(
    "This extension method is not required when using Kotlin 1.4. " +
            "You should remove \"import androidx.lifecycle.observe\""
)
@MainThread
inline fun <T> LiveData<T>.observe(
    owner: LifecycleOwner,
    crossinline onChanged: (T) -> Unit
): Observer<T> {
    val wrappedObserver = Observer<T> { t -> onChanged.invoke(t) }
    observe(owner, wrappedObserver)
    return wrappedObserver
}