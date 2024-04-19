package com.xxh.jetpacksample.lifecycle.source.savedstate

import android.os.Bundle
import androidx.annotation.MainThread
import com.xxh.jetpacksample.lifecycle.source.Lifecycle

/**
 * 对SavedStateRegistry的控制器，
 * 1、创建SavedStateRegistry对象，封装绑定，存储，恢复功能（封装相关功能，提供SavedStateRegistry对外的部分接口）。
 * 2、SavedStateRegistryOwner通过SavedStateRegistryController的 [SavedStateRegistry] 访问和执行操作
 * 工厂+策略模式
 *
 * An API for [SavedStateRegistryOwner] implementations to control [SavedStateRegistry].
 * 用于 [SavedStateRegistryOwner] 实现的 API 来控制 [SavedStateRegistry]。
 *
 * `SavedStateRegistryOwner` should call [performRestore] to restore state of
 * [SavedStateRegistry] and [performSave] to gather SavedState from it.
 * “SavedStateRegistryOwner”应该调用[performRestore]来恢复[SavedStateRegistry]的状态，[performSave]从中收集SavedState。
 *
 */
class SavedStateRegistryController private constructor(private val owner: SavedStateRegistryOwner) {

    /**
     * The [SavedStateRegistry] owned by this controller
     * 此控制器拥有的 [SavedStateRegistry]
     */
    val savedStateRegistry: SavedStateRegistry = SavedStateRegistry()

    private var attached = false

    /**
     * Perform the initial, one time attachment necessary to configure this
     * [SavedStateRegistry]. This must be called when the owner's [Lifecycle] is
     * [Lifecycle.State.INITIALIZED] and before you call [performRestore].
     * 执行配置此 [SavedStateRegistry] 所需的初始一次性附件。
     * 当所有者的 [Lifecycle] 为 [Lifecycle.State.INITIALIZED] 时和调用 [performRestore] 之前，必须调用此选项。
     */
    @MainThread
    fun performAttach() {
        val lifecycle = owner.lifecycle
        check(lifecycle.currentState == Lifecycle.State.INITIALIZED) {
            ("Restarter must be created only during owner's initialization stage")
        }
        lifecycle.addObserver(Recreator(owner))
        savedStateRegistry.performAttach(lifecycle)
        attached = true
    }

    /**
     * An interface for an owner of this [SavedStateRegistry] to restore saved state.
     * 此 [SavedStateRegistry] 的所有者用于恢复已保存状态的接口。
     * @param savedState restored state
     */
    @MainThread
    fun performRestore(savedState: Bundle?) {
        // To support backward compatibility with libraries that do not explicitly
        // call performAttach(), we make sure that work is done here
        if (!attached) {
            performAttach()
        }
        val lifecycle = owner.lifecycle
        check(!lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            ("performRestore cannot be called when owner is ${lifecycle.currentState}")
        }
        savedStateRegistry.performRestore(savedState)
    }

    /**
     * An interface for an owner of this  [SavedStateRegistry]
     * to perform state saving, it will call all registered providers and
     * merge with unconsumed state.
     * 此 [SavedStateRegistry] 的所有者执行状态保存的接口，它将调用所有已注册的提供程序并与未使用的状态合并。
     *
     * @param outBundle Bundle in which to place a saved state
     */
    @MainThread
    fun performSave(outBundle: Bundle) {
        savedStateRegistry.performSave(outBundle)
    }

    companion object {
        /**
         * Creates a [SavedStateRegistryController].
         *
         * It should be called during construction time of [SavedStateRegistryOwner]
         * 它应该在 [SavedStateRegistryOwner] 的构造期间调用
         */
        @JvmStatic
        fun create(owner: SavedStateRegistryOwner): SavedStateRegistryController {
            return SavedStateRegistryController(owner)
        }
    }
}