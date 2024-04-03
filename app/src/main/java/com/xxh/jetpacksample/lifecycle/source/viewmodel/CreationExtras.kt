package com.xxh.jetpacksample.lifecycle.source.viewmodel


/**
 * Simple map-like object that passed in [ViewModelProvider.Factory.create]
 * to provide an additional information to a factory.
 *
 * It allows making `Factory` implementations stateless, which makes an injection of factories
 * easier because  don't require all information be available at construction time.
 */
abstract class CreationExtras internal constructor() {
    internal val map: MutableMap<Key<*>, Any?> = mutableMapOf()

    /**
     * Key for the elements of [CreationExtras]. [T] is a type of an element with this key.
     */
    interface Key<T>

    /**
     * Returns an element associated with the given [key]
     */
    abstract operator fun <T> get(key: Key<T>): T?

    /**
     * Empty [CreationExtras]
     */
    object Empty : CreationExtras() {
        override fun <T> get(key: Key<T>): T? = null
    }
}

/**
 * Mutable implementation of [CreationExtras]
 *
 * @param initialExtras extras that will be filled into the resulting MutableCreationExtras
 */
class MutableCreationExtras(initialExtras: CreationExtras = Empty) : CreationExtras() {

    init {
        map.putAll(initialExtras.map)
    }

    /**
     * Associates the given [key] with [t]
     */
    operator fun <T> set(key: Key<T>, t: T) {
        map[key] = t
    }

    override fun <T> get(key: Key<T>): T? {
        @Suppress("UNCHECKED_CAST")
        return map[key] as T?
    }
}
