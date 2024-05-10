package com.xxh.learn.ui.layout.swipereresh

import androidx.annotation.IdRes
import androidx.navigation.fragment.findNavController
import com.xxh.common.ListBaseFragment
import com.xxh.learn.ui.R

class SwiperefreshFragment : ListBaseFragment() {
    override fun setData(): Array<String> = arrayOf(
        "RecyclerView",
        "ConstraintLayout",
        "ViewPager2"
    )

    override fun itemClickHandle(name: String) {
        @IdRes
        val resId: Int? = when (name) {
            "RecyclerView" -> R.id.action_lifecycleListFragment_to_recyclerViewFragment
            "ConstraintLayout" -> R.id.action_layoutListFragment_to_constraintLayoutFragment
            "ViewPager2" -> R.id.action_layoutListFragment_to_browseActivity
            else -> null
        }
        resId?.let {
            findNavController().navigate(it)
        }
    }

}