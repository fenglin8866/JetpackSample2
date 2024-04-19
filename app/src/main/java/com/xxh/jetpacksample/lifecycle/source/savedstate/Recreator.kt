package com.xxh.jetpacksample.lifecycle.source.savedstate

import android.os.Bundle
import com.xxh.jetpacksample.lifecycle.source.Lifecycle
import com.xxh.jetpacksample.lifecycle.source.LifecycleEventObserver
import com.xxh.jetpacksample.lifecycle.source.LifecycleOwner

/**
 * 定义生命周期观察者
 * 在ON_CREATE执行，并且只执行一次。
 * 获取SavedStateProvider对象，并根据类名反射创建对象调用onRecreated方法。
 */
internal class Recreator(
    private val owner: SavedStateRegistryOwner
) : LifecycleEventObserver {

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event != Lifecycle.Event.ON_CREATE) {
            throw AssertionError("Next event must be ON_CREATE")
        }
        source.lifecycle.removeObserver(this)
        val bundle: Bundle = owner.savedStateRegistry
            .consumeRestoredStateForKey(COMPONENT_KEY) ?: return
        val classes: MutableList<String> = bundle.getStringArrayList(CLASSES_KEY)
            ?: throw IllegalStateException(
                "Bundle with restored state for the component " +
                        "\"$COMPONENT_KEY\" must contain list of strings by the key " +
                        "\"$CLASSES_KEY\""
            )
        for (className: String in classes) {
            reflectiveNew(className)
        }
    }

    private fun reflectiveNew(className: String) {
        val clazz: Class<out SavedStateRegistry.AutoRecreated> =
            try {
                Class.forName(className, false, Recreator::class.java.classLoader)
                    .asSubclass(SavedStateRegistry.AutoRecreated::class.java)
            } catch (e: ClassNotFoundException) {
                throw RuntimeException("Class $className wasn't found", e)
            }
        val constructor =
            try {
                clazz.getDeclaredConstructor()
            } catch (e: NoSuchMethodException) {
                throw IllegalStateException(
                    "Class ${clazz.simpleName} must have " +
                            "default constructor in order to be automatically recreated", e
                )
            }
        constructor.isAccessible = true
        val newInstance: SavedStateRegistry.AutoRecreated =
            try {
                constructor.newInstance()
            } catch (e: Exception) {
                throw RuntimeException("Failed to instantiate $className", e)
            }
        newInstance.onRecreated(owner)
    }

    /**
     * 定义一个状态保存提供者，并注册到传入的SavedStateRegistry上。
     * 存储:个String数组（className：是SavedStateRegistry.AutoRecreated的子类）
     */
    internal class SavedStateProvider(registry: SavedStateRegistry) :
        SavedStateRegistry.SavedStateProvider {

        private val classes: MutableSet<String> = mutableSetOf()

        init {
            registry.registerSavedStateProvider(COMPONENT_KEY, this)
        }

        override fun saveState(): Bundle {
            return Bundle().apply {
                putStringArrayList(CLASSES_KEY, ArrayList(classes))
            }
        }

        fun add(className: String) {
            classes.add(className)
        }
    }

    companion object {
        const val CLASSES_KEY = "classes_to_restore"
        const val COMPONENT_KEY = "androidx.savedstate.Restarter"
    }
}