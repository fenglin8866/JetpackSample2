package com.xxh.jetpacksample.lifecycle.source.savedstate

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.annotation.MainThread
import com.xxh.jetpacksample.lifecycle.source.Lifecycle
import com.xxh.jetpacksample.lifecycle.source.LifecycleEventObserver
import com.xxh.jetpacksample.lifecycle.utils.SafeIterableMap


/**
 * 保存状态注册表：用于plugging组件的接口，消耗和提供保存状态。
 * An interface for plugging components that consumes and contributes to the saved state.
 *
 * This objects lifetime is bound to the lifecycle of owning component: when activity or
 * fragment is recreated, new instance of the object is created as well.
 * 此对象生存期绑定到拥有组件的生命周期：重新创建活动或片段时，也会创建对象的新实例。
 */
@SuppressLint("RestrictedApi")
class SavedStateRegistry internal constructor() {
    //通过key-value存储Bundle数据
    private val components = SafeIterableMap<String, SavedStateProvider>()
    //判断是否附加
    private var attached = false
    //恢复的Bundle数据
    private var restoredState: Bundle? = null

    /**
     * Whether the state was restored after creation and can be safely consumed
     * with [consumeRestoredStateForKey].
     * 状态是否在创建后还原，并且可以安全地使用 [consumeRerestoreStateForKey]。
     * [isRestored] == true if state was restored
     * true表示状态已经恢复
     */
    @get: MainThread
    var isRestored = false
        private set
    //recreator的提供者
    private var recreatorProvider: Recreator.SavedStateProvider? = null
    //判断是否允许保存状态
    internal var isAllowingSavingState = true

    /**
     * 在恢复的数据中，通过key获取对应的bundle对象，并移除key，使用同一个key下次调用返回null。
     * 必须在performRestore之后调用。
     *
     * Consumes saved state previously supplied by [SavedStateProvider] registered
     * via [registerSavedStateProvider] with the given `key`.
     * 使用以前由 [SavedStateProvider] 提供的已保存状态，该状态已通过 [registerSavedStateProvider] 注册，具有给定的“键”。
     *
     * This call clears an internal reference to returned saved state, so if you call it second time
     * in the row it will return `null`.
     * 此调用清除对返回的已保存状态的内部引用，因此如果您在行中第二次调用它，它将返回“null”。
     *
     * All unconsumed values will be saved during `onSaveInstanceState(Bundle savedState)`
     * 所有未使用的值将在“onSaveInstanceState（Bundle savedState）”期间保存
     *
     * This method can be called after `super.onCreate(savedStateBundle)` of the corresponding
     * component. Calling it before that will result in `IllegalArgumentException`.
     * [Lifecycle.Event.ON_CREATE] can be used as a signal
     * that a saved state can be safely consumed.
     * 此方法可以在相应组件的“super.onCreate（savedStateBundle）”之后调用。在此之前调用它将导致“非法参数异常”。
     * [Lifecycle.Event.ON_CREATE] 可用作可以安全使用保存状态的信号。
     *
     * @param key a key with which [SavedStateProvider] was previously registered.
     * @return `S` with the previously saved state or {@code null}
     */
    @MainThread
    fun consumeRestoredStateForKey(key: String): Bundle? {
        check(isRestored) {
            ("You can consumeRestoredStateForKey " +
                    "only after super.onCreate of corresponding component")
        }
        if (restoredState != null) {
            val result = restoredState?.getBundle(key)
            restoredState?.remove(key)
            if (restoredState?.isEmpty != false) {
                restoredState = null
            }
            return result
        }
        return null
    }

    /**
     * 通过key-value注册SavedStateProvider，同一个key多次注册抛异常。
     * Registers a [SavedStateProvider] by the given `key`. This
     * `savedStateProvider` will be called
     * during state saving phase, returned object will be associated with the given `key`
     * and can be used after the restoration via [.consumeRestoredStateForKey].
     * 通过给定的“key”注册 [SavedStateProvider]。此“savedStateProvider”将在状态保存阶段调用，
     * 返回的对象将与给定的“key”相关联，并且可以在恢复后通过 [.consumeRestoredStateForKey] 使用。
     *
     * If there is unconsumed value with the same `key`,
     * the value supplied by `savedStateProvider` will be overridden and
     * will be written to resulting saved state.
     * 如果存在具有相同“key”的未使用值，则“savedStateProvider”提供的值将被覆盖，并将写入生成的保存状态。
     *
     * If a provider was already registered with the given `key`, an implementation should
     * throw an [IllegalArgumentException]
     * 如果provider已经使用给定的“key”注册，则实现应抛出 [IllegalArgumentException]
     *
     * @param key      a key with which returned saved state will be associated
     * @param provider savedStateProvider to get saved state.
     */
    @MainThread
    fun registerSavedStateProvider(
        key: String,
        provider: SavedStateProvider
    ) {
        val previous = components.putIfAbsent(key, provider)
        require(previous == null) {
            ("SavedStateProvider with the given key is" +
                    " already registered")
        }
    }

    /**
     * 通过key获取之前注册的SavedStateProvider
     *
     * Get a previously registered [SavedStateProvider].
     *
     * @param key The key used to register the [SavedStateProvider] when it was registered
     *            with registerSavedStateProvider(String, SavedStateProvider).
     *
     * Returns the [SavedStateProvider] previously registered with
     * [registerSavedStateProvider] or null if no provider
     * has been registered with the given key.
     */
    fun getSavedStateProvider(key: String): SavedStateProvider? {
        var provider: SavedStateProvider? = null
        for ((k, value) in components) {
            if (k == key) {
                provider = value
                break
            }
        }
        return provider
    }

    /**
     * Unregisters a component previously registered by the given `key`
     *
     * @param key a key with which a component was previously registered.
     */
    @MainThread
    fun unregisterSavedStateProvider(key: String) {
        components.remove(key)
    }

    /**
     * Subclasses of this interface will be automatically recreated if they were previously
     * registered via [runOnNextRecreation].
     * 如果此接口的子类以前通过 [runOnNextRecreation] 注册，则会自动重新创建。
     * Subclasses must have a default constructor
     * 子类必须具有默认构造函数
     */
    interface AutoRecreated {
        /**
         * This method will be called during
         * dispatching of [androidx.lifecycle.Lifecycle.Event.ON_CREATE] of owning component
         * which was restarted
         * 此方法将在调度重新启动的拥有组件的 [androidx.lifecycle.Lifecycle.Event.ON_CREATE] 期间调用
         *
         * @param owner a component that was restarted
         * 重新启动的组件
         */
        fun onRecreated(owner: SavedStateRegistryOwner)
    }

    /**
     * Executes the given class when the owning component restarted.
     * 在所属组件重新启动时执行给定的类。
     *
     * The given class will be automatically instantiated via default constructor and method
     * [AutoRecreated.onRecreated] will be called.
     * It is called as part of dispatching of [androidx.lifecycle.Lifecycle.Event.ON_CREATE]
     * event.
     *  给定的类将通过默认构造函数自动实例化，并将调用方法 [AutoRecreated.onRecreated]。
     *  它被称为 [androidx.lifecycle.Lifecycle.Event.ON_CREATE] 事件调度的一部分。
     *
     * @param clazz that will need to be instantiated on the next component recreation
     * @throws IllegalArgumentException if you try to call if after [Lifecycle.Event.ON_STOP]
     *                               was dispatched
     */
    @MainThread
    fun runOnNextRecreation(clazz: Class<out AutoRecreated>) {
        check(isAllowingSavingState) { "Can not perform this action after onSaveInstanceState" }
        recreatorProvider = recreatorProvider ?: Recreator.SavedStateProvider(this)
        try {
            clazz.getDeclaredConstructor()
        } catch (e: NoSuchMethodException) {
            throw IllegalArgumentException(
                "Class ${clazz.simpleName} must have " +
                        "default constructor in order to be automatically recreated", e
            )
        }
        recreatorProvider?.add(clazz.name)
    }

    /**
     * An interface for an owner of this [SavedStateRegistry] to attach this
     * to a [Lifecycle].
     * 此 [SavedStateRegistry] 的所有者将其附加到 [Lifecycle] 的接口。
     */
    @MainThread
    internal fun performAttach(lifecycle: Lifecycle) {
        check(!attached) { "SavedStateRegistry was already attached." }

        //lifecycle在destroyed状态自动移除所有的LifecycleObserver，LifecycleObserver不用执行remove操作
        lifecycle.addObserver(LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                isAllowingSavingState = true
            } else if (event == Lifecycle.Event.ON_STOP) {
                isAllowingSavingState = false
            }
        })
        attached = true
    }

    /**
     * An interface for an owner of this [SavedStateRegistry] to restore saved state.
     * 执行恢复操作，必须在performAttach之后。
     */
    @MainThread
    internal fun performRestore(savedState: Bundle?) {
        check(attached) {
            ("You must call performAttach() before calling " +
                    "performRestore(Bundle).")
        }
        check(!isRestored) { "SavedStateRegistry was already restored." }
        restoredState = savedState?.getBundle(SAVED_COMPONENTS_KEY)

        isRestored = true
    }

    /**
     * 保存状态。通过外部传入outBundle，通过固定的key将SaveState的Bundle（ components + restoredState，如果存在相同的key，value进行替换）传入到outBundle中
     * An interface for an owner of this [SavedStateRegistry]
     * to perform state saving, it will call all registered providers and
     * merge with unconsumed state.
     * 此 [SavedStateRegistry] 的所有者执行状态保存的接口，它将调用所有已注册的提供程序并与未使用的状态合并。
     *
     * @param outBundle Bundle in which to place a saved state
     * @suppress INACCESSIBLE_TYPE iterator is used strictly as Iterator, does not access
     * inaccessible type IteratorWithAdditions
     */
    @MainThread
    @Suppress("INACCESSIBLE_TYPE")
    fun performSave(outBundle: Bundle) {
        val components = Bundle()
        if (restoredState != null) {
            components.putAll(restoredState)
        }
        val it: Iterator<Map.Entry<String, SavedStateProvider>> =
            this.components.iteratorWithAdditions()
        while (it.hasNext()) {
            val (key, value) = it.next()
            components.putBundle(key, value.saveState())
        }
        if (!components.isEmpty) {
            outBundle.putBundle(SAVED_COMPONENTS_KEY, components)
        }
    }

    /**
     * This interface marks a component that contributes to saved state.
     * 此接口标记有助于保存状态的组件。
     */
    fun interface SavedStateProvider {
        /**
         * Called to retrieve a state from a component before being killed
         * so later the state can be received from [consumeRestoredStateForKey]
         * 被杀死之前调用，从组件中检索状态，以便以后可以从 [consumeRerestoreStateForKey] 接收状态
         *
         * Returns `S` with your saved state.
         */
        fun saveState(): Bundle
    }

    private companion object {
        private const val SAVED_COMPONENTS_KEY =
            "androidx.lifecycle.BundlableSavedStateRegistry.key"
    }
}