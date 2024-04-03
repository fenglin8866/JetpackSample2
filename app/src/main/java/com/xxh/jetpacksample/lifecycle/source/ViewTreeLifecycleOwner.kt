@file:JvmName("ViewTreeLifecycleOwner")
package com.xxh.jetpacksample.lifecycle.source

import android.view.View
import com.xxh.jetpacksample.R

/**
 * Set the [LifecycleOwner] responsible for managing the given [View].
 * Calls to [get] from this view or descendants will return `lifecycleOwner`.
 *
 * This should only be called by constructs such as activities or fragments that manage
 * a view tree and reflect their own lifecycle through a [LifecycleOwner]. Callers
 * should only set a [LifecycleOwner] that will be *stable.* The associated
 * lifecycle should report that it is destroyed if the view tree is removed and is not
 * guaranteed to later become reattached to a window.
 *
 * @param lifecycleOwner LifecycleOwner representing the manager of the given view
 */
@JvmName("set")
fun View.setViewTreeLifecycleOwner(lifecycleOwner: LifecycleOwner?) {
    setTag(R.id.view_tree_lifecycle_owner, lifecycleOwner)
}

/**
 * Retrieve the [LifecycleOwner] responsible for managing the given [View].
 * This may be used to scope work or heavyweight resources associated with the view
 * that may span cycles of the view becoming detached and reattached from a window.
 *
 * @return The [LifecycleOwner] responsible for managing this view and/or some subset
 * of its ancestors
 */
@JvmName("get")
fun View.findViewTreeLifecycleOwner(): LifecycleOwner? {
    return generateSequence(this) { currentView ->
        currentView.parent as? View
    }.mapNotNull { viewParent ->
        viewParent.getTag(R.id.view_tree_lifecycle_owner) as? LifecycleOwner
    }.firstOrNull()
}