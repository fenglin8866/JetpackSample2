package com.xxh.jetpacksample.lifecycle.sourcesample
import androidx.annotation.MainThread

abstract class XLifecycle {

    @MainThread
    abstract fun addObserver(observer: XLifecycleObserver)

    @MainThread
    abstract fun removeObserver(observer: XLifecycleObserver)

    @get:MainThread
    abstract val currentState: State

    enum class Event {
        ON_CREATE, ON_START, ON_RESUME, ON_PAUSE, ON_STOP, ON_DESTROY, ON_ANY;

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
             * Returns the [XLifecycle.Event] that will be reported by a [XLifecycle]
             * leaving the specified [XLifecycle.State] to a lower state, or `null`
             * if there is no valid event that can move down from the given state.
             *
             * @param state the higher state that the returned event will transition down from
             * @return the event moving down the XLifecycle phases from state
             */
            @JvmStatic
            fun downFrom(state: XLifecycle.State): XLifecycle.Event? {
                return when (state) {
                    XLifecycle.State.CREATED -> XLifecycle.Event.ON_DESTROY
                    XLifecycle.State.STARTED -> XLifecycle.Event.ON_STOP
                    XLifecycle.State.RESUMED -> XLifecycle.Event.ON_PAUSE
                    else -> null
                }
            }

            /**
             * Returns the [XLifecycle.Event] that will be reported by a [XLifecycle]
             * entering the specified [XLifecycle.State] from a higher state, or `null`
             * if there is no valid event that can move down to the given state.
             *
             * @param state the lower state that the returned event will transition down to
             * @return the event moving down the XLifecycle phases to state
             */
            @JvmStatic
            fun downTo(state: XLifecycle.State): XLifecycle.Event? {
                return when (state) {
                    XLifecycle.State.DESTROYED -> XLifecycle.Event.ON_DESTROY
                    XLifecycle.State.CREATED -> XLifecycle.Event.ON_STOP
                    XLifecycle.State.STARTED -> XLifecycle.Event.ON_PAUSE
                    else -> null
                }
            }

            /**
             * Returns the [XLifecycle.Event] that will be reported by a [XLifecycle]
             * leaving the specified [XLifecycle.State] to a higher state, or `null`
             * if there is no valid event that can move up from the given state.
             *
             * @param state the lower state that the returned event will transition up from
             * @return the event moving up the XLifecycle phases from state
             */
            @JvmStatic
            fun upFrom(state: XLifecycle.State): XLifecycle.Event? {
                return when (state) {
                    XLifecycle.State.INITIALIZED -> XLifecycle.Event.ON_CREATE
                    XLifecycle.State.CREATED -> XLifecycle.Event.ON_START
                    XLifecycle.State.STARTED -> XLifecycle.Event.ON_RESUME
                    else -> null
                }
            }

            /**
             * Returns the [XLifecycle.Event] that will be reported by a [XLifecycle]
             * entering the specified [XLifecycle.State] from a lower state, or `null`
             * if there is no valid event that can move up to the given state.
             *
             * @param state the higher state that the returned event will transition up to
             * @return the event moving up the XLifecycle phases to state
             */
            @JvmStatic
            fun upTo(state: XLifecycle.State): XLifecycle.Event? {
                return when (state) {
                    XLifecycle.State.CREATED -> XLifecycle.Event.ON_CREATE
                    XLifecycle.State.STARTED -> XLifecycle.Event.ON_START
                    XLifecycle.State.RESUMED -> XLifecycle.Event.ON_RESUME
                    else -> null
                }
            }
        }
    }

    enum class State {
        DESTROYED,
        INITIALIZED,
        CREATED,
        STARTED,
        RESUMED;

        fun isAtLeast(state: State): Boolean {
            return compareTo(state) >= 0
        }

    }

}