package com.xxh.jetpacksample.lifecycle.source.viewmodel

import kotlin.reflect.KClass


@DslMarker
annotation class ViewModelFactoryDsl

/**
 * Creates an [InitializerViewModelFactory] with the initializers provided in the builder.
 */
inline fun viewModelFactory(
    builder: InitializerViewModelFactoryBuilder.() -> Unit
): ViewModelProvider.Factory = InitializerViewModelFactoryBuilder().apply(builder).build()

/**
 * 用于构建InitializerViewModelFactory的建造器
 * DSL for constructing a new [ViewModelProvider.Factory]
 */
@ViewModelFactoryDsl
class InitializerViewModelFactoryBuilder {
    private val initializers = mutableListOf<ViewModelInitializer<*>>()

    /**
     * Add the initializer for the given ViewModel class.
     *
     * @param clazz the class the initializer is associated with.
     * @param initializer lambda used to create an instance of the ViewModel class
     */
    fun <T : ViewModel> addInitializer(clazz: KClass<T>, initializer: CreationExtras.() -> T) {
        initializers.add(ViewModelInitializer(clazz.java, initializer))
    }

    /**
     * Build the InitializerViewModelFactory.
     */
    fun build(): ViewModelProvider.Factory =
        InitializerViewModelFactory(*initializers.toTypedArray())
}

/**
 * 向InitializerViewModelFactoryBuilder中添加initializer
 * Add an initializer to the [InitializerViewModelFactoryBuilder]
 */
inline fun <reified VM : ViewModel> InitializerViewModelFactoryBuilder.initializer(
    noinline initializer: CreationExtras.() -> VM
) {
    addInitializer(VM::class, initializer)
}

/**
 * Holds a [ViewModel] class and initializer for that class
 * 保存 [ViewModel] 类和该类的初始值设定项
 */
class ViewModelInitializer<T : ViewModel>(
    internal val clazz: Class<T>,
    internal val initializer: CreationExtras.() -> T,
)

/**
 * 一个 [ViewModelProvider.Factory]，允许您添加 lambda 初始值设定项以使用 [CreationExtras] 处理特定的 ViewModel 类，同时使用任何其他类的默认行为。
 *
 * A [ViewModelProvider.Factory] that allows you to add lambda initializers for handling
 * particular ViewModel classes using [CreationExtras], while using the default behavior for any
 * other classes.
 *
 * ```
 * val factory = viewModelFactory {
 *   initializer { TestViewModel(this[key]) }
 * }
 * val viewModel: TestViewModel = factory.create(TestViewModel::class.java, extras)
 * ```
 */
internal class InitializerViewModelFactory(
    private vararg val initializers: ViewModelInitializer<*>
) : ViewModelProvider.Factory {

    /**
     * Creates a new instance of the given `Class`.
     *
     * This will use the initializer if one has been set for the class, otherwise it throw an
     * [IllegalArgumentException].
     *
     * @param modelClass a `Class` whose instance is requested
     * @param extras an additional information for this creation request
     * @return a newly created ViewModel
     *
     * @throws IllegalArgumentException if no initializer has been set for the given class.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        var viewModel: T? = null
        @Suppress("UNCHECKED_CAST")
        initializers.forEach {
            if (it.clazz == modelClass) {
                viewModel = it.initializer.invoke(extras) as? T
            }
        }
        return viewModel ?: throw IllegalArgumentException(
            "No initializer set for given class ${modelClass.name}"
        )
    }
}