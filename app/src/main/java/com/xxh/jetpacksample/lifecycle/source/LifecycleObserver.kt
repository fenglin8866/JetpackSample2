package com.xxh.jetpacksample.lifecycle.source

/**
 * Marks a class as a LifecycleObserver. Don't use this interface directly. Instead implement either
 * [DefaultLifecycleObserver] or [LifecycleEventObserver] to be notified about
 * lifecycle events.
 *
 * @see Lifecycle Lifecycle - for samples and usage patterns.
 *
 * 标记生命周期观察者，不能直接使用，使用其子类DefaultLifecycleObserver或者LifecycleEventObserver
 * 接收有关生命周期的事件通知
 */
interface LifecycleObserver {
}