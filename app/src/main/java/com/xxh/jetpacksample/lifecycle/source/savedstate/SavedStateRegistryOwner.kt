package com.xxh.jetpacksample.lifecycle.source.savedstate

import com.xxh.jetpacksample.lifecycle.source.LifecycleOwner

/**
 * A scope that owns [SavedStateRegistry]
 * 拥有 [SavedStateRegistry] 的作用域
 *
 * This owner should be passed in to create a [SavedStateRegistryController] object
 * through which this owner can access and perform operations via the
 * controller's [SavedStateRegistry]
 *  应传入此所有者以创建 [SavedStateRegistryController] 对象，通过该对象，此所有者可以通过控制器的 [SavedStateRegistry] 访问和执行操作
 *
 * [SavedStateRegistryController.performAttach] must be called once (and only once) on the
 * main thread during the owner's [Lifecycle.State.INITIALIZED] state.
 * It should be called before you call [SavedStateRegistryController.performRestore]
 * 在所有者的 [Lifecycle.State.INITIALIZED] 状态下，必须在主线程上调用一次（且仅调用一次）[SavedStateRegistryController.performAttach]。
 * 应在调用 [SavedStateRegistryController.performRestore] 之前调用它
 *
 * [SavedStateRegistryController.performRestore] can be called with a nullable if nothing
 * needs to be restored, or with the state Bundle to be restored. performRestore can be called
 * in one of two places:
 * 1. Directly before the Lifecycle moves to [Lifecycle.State.CREATED]
 * 2. Before [Lifecycle.State.STARTED] is reached, as part of the [LifecycleObserver]
 * that is added during owner initialization
 * [SavedStateRegistryController.performRestore] 如果不需要还原任何内容，则可以使用 nullable 调用，或者使用要还原的状态 Bundle 调用。
 * 可以在以下两个位置之一调用 performRestore：
 * 1. 在生命周期移动到 [Lifecycle.State.CREATED] 之前
 * 2. 在达到 [Lifecycle.State.STARTED] 之前，
 * 作为所有者初始化期间添加的 [LifecycleObserver] 的一部分
 *
 *
 * [SavedStateRegistryController.performSave] should be called after owner has been stopped but
 * before it reaches [Lifecycle.State.DESTROYED] state. Hence it should only be called once the
 * owner has received the [Lifecycle.Event.ON_STOP] event. The bundle passed to performSave
 * will be the bundle restored by performRestore.
 * [SavedStateRegistryController.performSave] 应在所有者停止后但在达到 [Lifecycle.State.DESTROYED] 状态之前调用。
 * 因此，只有在所有者收到 [Lifecycle.Event.ON_STOP] 事件后，才应调用它。传递给 performSave 的捆绑包将是 performRestore 还原的捆绑包。
 *
 * @see [ViewTreeSavedStateRegistryOwner]
 */
interface SavedStateRegistryOwner : LifecycleOwner {
    /**
     * The [SavedStateRegistry] owned by this SavedStateRegistryOwner
     */
    val savedStateRegistry: SavedStateRegistry
}