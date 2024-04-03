package com.xxh.jetpacksample.lifecycle.source.livedata

import android.annotation.SuppressLint
import androidx.annotation.RestrictTo
import androidx.annotation.VisibleForTesting
import androidx.annotation.WorkerThread
import androidx.arch.core.executor.ArchTaskExecutor
import java.util.concurrent.Executor
import java.util.concurrent.atomic.AtomicBoolean

/**
 * A LiveData class that can be invalidated & computed when there are active observers.
 *
 * It can be invalidated via [invalidate], which will result in a call to
 * [compute] if there are active observers (or when they start observing)
 *
 * This is an internal class for now, might be public if we see the necessity.
 *
 * @param <T> The type of the live data
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
abstract class ComputableLiveData<T> @JvmOverloads
/**
 * Creates a computable live data that computes values on the specified executor
 * or the arch IO thread executor by default.
 *
 * @param executor Executor that is used to compute new LiveData values.
 */
constructor(
    internal val executor: Executor = ArchTaskExecutor.getIOThreadExecutor()
) {

    private val _liveData: LiveData<T?> =
        object : LiveData<T?>() {
            override fun onActive() {
                executor.execute(refreshRunnable)
            }
        }

    /**
     * The LiveData managed by this class.
     */
    open val liveData: LiveData<T?> = _liveData
    internal val invalid = AtomicBoolean(true)
    internal val computing = AtomicBoolean(false)

    @JvmField
    @VisibleForTesting
    internal val refreshRunnable = Runnable {
        var computed: Boolean
        do {
            computed = false
            // compute can happen only in 1 thread but no reason to lock others.
            if (computing.compareAndSet(false, true)) {
                // as long as it is invalid, keep computing.
                try {
                    var value: T? = null
                    while (invalid.compareAndSet(true, false)) {
                        computed = true
                        value = compute()
                    }
                    if (computed) {
                        liveData.postValue(value)
                    }
                } finally {
                    // release compute lock
                    computing.set(false)
                }
            }
            // check invalid after releasing compute lock to avoid the following scenario.
            // Thread A runs compute()
            // Thread A checks invalid, it is false
            // Main thread sets invalid to true
            // Thread B runs, fails to acquire compute lock and skips
            // Thread A releases compute lock
            // We've left invalid in set state. The check below recovers.
        } while (computed && invalid.get())
    }

    // invalidation check always happens on the main thread
    @JvmField
    @VisibleForTesting
    internal val invalidationRunnable = Runnable {
        val isActive = liveData.hasActiveObservers()
        if (invalid.compareAndSet(false, true)) {
            if (isActive) {
                executor.execute(refreshRunnable)
            }
        }
    }

    /**
     * Invalidates the LiveData.
     *
     * When there are active observers, this will trigger a call to [.compute].
     */
    @SuppressLint("RestrictedApi")
    open fun invalidate() {
        ArchTaskExecutor.getInstance().executeOnMainThread(invalidationRunnable)
    }

    // TODO https://issuetracker.google.com/issues/112197238
    @WorkerThread
    protected abstract fun compute(): T
}