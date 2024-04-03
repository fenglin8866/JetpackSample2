package com.xxh.jetpacksample.lifecycle.source

import androidx.annotation.MainThread
import java.util.concurrent.atomic.AtomicReference
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

/**
 * Defines an object that has an Android Lifecycle. [Fragment][androidx.fragment.app.Fragment]
 * and [FragmentActivity][androidx.fragment.app.FragmentActivity] classes implement
 * [LifecycleOwner] interface which has the [ getLifecycle][LifecycleOwner.getLifecycle] method to access the Lifecycle. You can also implement [LifecycleOwner]
 * in your own classes.
 *
 * [Event.ON_CREATE], [Event.ON_START], [Event.ON_RESUME] events in this class
 * are dispatched **after** the [LifecycleOwner]'s related method returns.
 * [Event.ON_PAUSE], [Event.ON_STOP], [Event.ON_DESTROY] events in this class
 * are dispatched **before** the [LifecycleOwner]'s related method is called.
 * For instance, [Event.ON_START] will be dispatched after
 * [onStart][android.app.Activity.onStart] returns, [Event.ON_STOP] will be dispatched
 * before [onStop][android.app.Activity.onStop] is called.
 * This gives you certain guarantees on which state the owner is in.
 *
 * To observe lifecycle events call [.addObserver] passing an object
 * that implements either [DefaultLifecycleObserver] or [LifecycleEventObserver].
 */
abstract class Lifecycle {
    /**
     * Lifecycle coroutines extensions stashes the CoroutineScope into this field.
     *
     * RestrictTo as it is used by lifecycle-common-ktx
     */
    var internalScopeRef: AtomicReference<Any> = AtomicReference<Any>()

    /**
     * Adds a LifecycleObserver that will be notified when the LifecycleOwner changes
     * state.
     *
     * The given observer will be brought to the current state of the LifecycleOwner.
     * For example, if the LifecycleOwner is in [State.STARTED] state, the given observer
     * will receive [Event.ON_CREATE], [Event.ON_START] events.
     *
     * @param observer The observer to notify.
     */
    @MainThread
    abstract fun addObserver(observer: LifecycleObserver)

    /**
     * Removes the given observer from the observers list.
     *
     * If this method is called while a state change is being dispatched,
     *
     *  * If the given observer has not yet received that event, it will not receive it.
     *  * If the given observer has more than 1 method that observes the currently dispatched
     * event and at least one of them received the event, all of them will receive the event and
     * the removal will happen afterwards.
     *
     *
     * @param observer The observer to be removed.
     */
    @MainThread
    abstract fun removeObserver(observer: LifecycleObserver)

    /**
     * Returns the current state of the Lifecycle.
     *
     * @return The current state of the Lifecycle.
     */
    @get:MainThread
    abstract val currentState: State

    /**
     * Returns a [StateFlow] where the [StateFlow.value] represents
     * the current [State] of this Lifecycle.
     *
     * @return [StateFlow] where the [StateFlow.value] represents
     * the current [State] of this Lifecycle.
     */
    open val currentStateFlow: StateFlow<State>
        get() {
            val mutableStateFlow = MutableStateFlow(currentState)
            //通过监听器切换不同的状态
            LifecycleEventObserver { _, event ->
                mutableStateFlow.value = event.targetState
            }.also { addObserver(it) }
            return mutableStateFlow.asStateFlow()
        }

    enum class Event {
        /**
         * Constant for onCreate event of the [LifecycleOwner].
         */
        ON_CREATE,

        /**
         * Constant for onStart event of the [LifecycleOwner].
         */
        ON_START,

        /**
         * Constant for onResume event of the [LifecycleOwner].
         */
        ON_RESUME,

        /**
         * Constant for onPause event of the [LifecycleOwner].
         */
        ON_PAUSE,

        /**
         * Constant for onStop event of the [LifecycleOwner].
         */
        ON_STOP,

        /**
         * Constant for onDestroy event of the [LifecycleOwner].
         */
        ON_DESTROY,

        /**
         * An [Event] constant that can be used to match all events.
         */
        ON_ANY;

        /**
         * Returns the new [Lifecycle.State] of a [Lifecycle] that just reported
         * this [Lifecycle.Event].
         *
         * Throws [IllegalArgumentException] if called on [.ON_ANY], as it is a special
         * value used by [OnLifecycleEvent] and not a real lifecycle event.
         *
         * @return the state that will result from this event
         */
        val targetState: State
            get() {
                when (this) {
                    ON_CREATE, ON_STOP -> return State.CREATED
                    ON_START, ON_PAUSE -> return State.STARTED
                    ON_RESUME -> return State.RESUMED
                    ON_DESTROY -> return State.DESTROYED
                    ON_ANY -> {}
                }
                throw IllegalArgumentException("$this has no target state")
            }

        /**
         * down：针对状态的逆向变化
         * up：针对状态的正向变化
         * from：从前状态到下一个状态的事件
         * to：从上一个状态到当前状态的事件
         */
        companion object {
            /**
             * Returns the [Lifecycle.Event] that will be reported by a [Lifecycle]
             * leaving the specified [Lifecycle.State] to a lower state, or `null`
             * if there is no valid event that can move down from the given state.
             *
             * @param state the higher state that the returned event will transition down from
             * @return the event moving down the lifecycle phases from state
             */
            @JvmStatic
            fun downFrom(state: State): Event? {
                return when (state) {
                    State.CREATED -> ON_DESTROY
                    State.STARTED -> ON_STOP
                    State.RESUMED -> ON_PAUSE
                    else -> null
                }
            }

            /**
             * Returns the [Lifecycle.Event] that will be reported by a [Lifecycle]
             * entering the specified [Lifecycle.State] from a higher state, or `null`
             * if there is no valid event that can move down to the given state.
             *
             * @param state the lower state that the returned event will transition down to
             * @return the event moving down the lifecycle phases to state
             */
            @JvmStatic
            fun downTo(state: State): Event? {
                return when (state) {
                    State.DESTROYED -> ON_DESTROY
                    State.CREATED -> ON_STOP
                    State.STARTED -> ON_PAUSE
                    else -> null
                }
            }

            /**
             * Returns the [Lifecycle.Event] that will be reported by a [Lifecycle]
             * leaving the specified [Lifecycle.State] to a higher state, or `null`
             * if there is no valid event that can move up from the given state.
             *
             * @param state the lower state that the returned event will transition up from
             * @return the event moving up the lifecycle phases from state
             */
            @JvmStatic
            fun upFrom(state: State): Event? {
                return when (state) {
                    State.INITIALIZED -> ON_CREATE
                    State.CREATED -> ON_START
                    State.STARTED -> ON_RESUME
                    else -> null
                }
            }

            /**
             * Returns the [Lifecycle.Event] that will be reported by a [Lifecycle]
             * entering the specified [Lifecycle.State] from a lower state, or `null`
             * if there is no valid event that can move up to the given state.
             *
             * @param state the higher state that the returned event will transition up to
             * @return the event moving up the lifecycle phases to state
             */
            @JvmStatic
            fun upTo(state: State): Event? {
                return when (state) {
                    State.CREATED -> ON_CREATE
                    State.STARTED -> ON_START
                    State.RESUMED -> ON_RESUME
                    else -> null
                }
            }
        }
    }

    /**
     * Lifecycle states. You can consider the states as the nodes in a graph and
     * [Event]s as the edges between these nodes.
     *
     * 注意状态顺序的重要性，用于判断当前状态。
     */
    enum class State {
        /**
         * Destroyed state for a LifecycleOwner. After this event, this Lifecycle will not dispatch
         * any more events. For instance, for an [android.app.Activity], this state is reached
         * **right before** Activity's [onDestroy][android.app.Activity.onDestroy] call.
         */
        DESTROYED,

        /**
         * Initialized state for a LifecycleOwner. For an [android.app.Activity], this is
         * the state when it is constructed but has not received
         * [onCreate][android.app.Activity.onCreate] yet.
         */
        INITIALIZED,

        /**
         * Created state for a LifecycleOwner. For an [android.app.Activity], this state
         * is reached in two cases:
         *
         *  * after [onCreate][android.app.Activity.onCreate] call;
         *  * **right before** [onStop][android.app.Activity.onStop] call.
         *
         */
        CREATED,

        /**
         * Started state for a LifecycleOwner. For an [android.app.Activity], this state
         * is reached in two cases:
         *
         *  * after [onStart][android.app.Activity.onStart] call;
         *  * **right before** [onPause][android.app.Activity.onPause] call.
         *
         */
        STARTED,

        /**
         * Resumed state for a LifecycleOwner. For an [android.app.Activity], this state
         * is reached after [onResume][android.app.Activity.onResume] is called.
         */
        RESUMED;

        /**
         * Compares if this State is greater or equal to the given `state`.
         *
         * @param state State to compare with
         * @return true if this State is greater or equal to the given `state`
         *
         * 与传入状态的比较。
         */
        fun isAtLeast(state: State): Boolean {
            return compareTo(state) >= 0
        }
    }
}

/**
 * [CoroutineScope] tied to this [Lifecycle].
 *
 * This scope will be cancelled when the [Lifecycle] is destroyed.
 *
 * This scope is bound to
 * [Dispatchers.Main.immediate][kotlinx.coroutines.MainCoroutineDispatcher.immediate]
 */
val Lifecycle.coroutineScope: LifecycleCoroutineScope
    get() {
        while (true) {
            val existing = internalScopeRef.get() as LifecycleCoroutineScopeImpl?
            if (existing != null) {
                return existing
            }
            val newScope = LifecycleCoroutineScopeImpl(
                this,
                SupervisorJob() + Dispatchers.Main.immediate
            )
            if (internalScopeRef.compareAndSet(null, newScope)) {
                newScope.register()
                return newScope
            }
        }
    }

/**
 * [CoroutineScope] tied to a [Lifecycle] and
 * [Dispatchers.Main.immediate][kotlinx.coroutines.MainCoroutineDispatcher.immediate]
 *
 * This scope will be cancelled when the [Lifecycle] is destroyed.
 *
 * This scope provides specialised versions of `launch`: [launchWhenCreated], [launchWhenStarted],
 * [launchWhenResumed]
 */
abstract class LifecycleCoroutineScope internal constructor() : CoroutineScope {
    internal abstract val lifecycle: Lifecycle

    /**
     * Launches and runs the given block when the [Lifecycle] controlling this
     * [LifecycleCoroutineScope] is at least in [Lifecycle.State.CREATED] state.
     *
     * The returned [Job] will be cancelled when the [Lifecycle] is destroyed.
     *
     * @see Lifecycle.whenCreated
     * @see Lifecycle.coroutineScope
     */
    @Deprecated(
        message = "launchWhenCreated is deprecated as it can lead to wasted resources " +
                "in some cases. Replace with suspending repeatOnLifecycle to run the block whenever " +
                "the Lifecycle state is at least Lifecycle.State.CREATED."
    )
    @Suppress("DEPRECATION")
    fun launchWhenCreated(block: suspend CoroutineScope.() -> Unit): Job = launch {
        lifecycle.whenCreated(block)
    }

    /**
     * Launches and runs the given block when the [Lifecycle] controlling this
     * [LifecycleCoroutineScope] is at least in [Lifecycle.State.STARTED] state.
     *
     * The returned [Job] will be cancelled when the [Lifecycle] is destroyed.
     *
     * @see Lifecycle.whenStarted
     * @see Lifecycle.coroutineScope
     */
    @Deprecated(
        message = "launchWhenStarted is deprecated as it can lead to wasted resources " +
                "in some cases. Replace with suspending repeatOnLifecycle to run the block whenever " +
                "the Lifecycle state is at least Lifecycle.State.STARTED."
    )
    @Suppress("DEPRECATION")
    fun launchWhenStarted(block: suspend CoroutineScope.() -> Unit): Job = launch {
        lifecycle.whenStarted(block)
    }

    /**
     * Launches and runs the given block when the [Lifecycle] controlling this
     * [LifecycleCoroutineScope] is at least in [Lifecycle.State.RESUMED] state.
     *
     * The returned [Job] will be cancelled when the [Lifecycle] is destroyed.
     *
     * @see Lifecycle.whenResumed
     * @see Lifecycle.coroutineScope
     */
    @Deprecated(
        message = "launchWhenResumed is deprecated as it can lead to wasted resources " +
                "in some cases. Replace with suspending repeatOnLifecycle to run the block whenever " +
                "the Lifecycle state is at least Lifecycle.State.RESUMED."
    )
    @Suppress("DEPRECATION")
    fun launchWhenResumed(block: suspend CoroutineScope.() -> Unit): Job = launch {
        lifecycle.whenResumed(block)
    }
}

internal class LifecycleCoroutineScopeImpl(
    override val lifecycle: Lifecycle,
    override val coroutineContext: CoroutineContext
) : LifecycleCoroutineScope(), LifecycleEventObserver {
    init {
        // in case we are initialized on a non-main thread, make a best effort check before
        // we return the scope. This is not sync but if developer is launching on a non-main
        // dispatcher, they cannot be 100% sure anyways.
        if (lifecycle.currentState == Lifecycle.State.DESTROYED) {
            coroutineContext.cancel()
        }
    }

    fun register() {
        launch(Dispatchers.Main.immediate) {
            if (lifecycle.currentState >= Lifecycle.State.INITIALIZED) {
                lifecycle.addObserver(this@LifecycleCoroutineScopeImpl)
            } else {
                coroutineContext.cancel()
            }
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (lifecycle.currentState <= Lifecycle.State.DESTROYED) {
            lifecycle.removeObserver(this)
            coroutineContext.cancel()
        }
    }
}

/**
 * Creates a [Flow] of [Event]s containing values dispatched by this [Lifecycle].
 */
val Lifecycle.eventFlow: Flow<Lifecycle.Event>
    get() = callbackFlow {
        val observer = LifecycleEventObserver { _, event ->
            trySend(event)
        }.also { addObserver(it) }

        awaitClose { removeObserver(observer) }
    }.flowOn(Dispatchers.Main.immediate)
