package com.xxh.learn.ui.layout

import androidx.annotation.IdRes
import androidx.navigation.fragment.findNavController
import com.xxh.common.ListBaseFragment
import com.xxh.learn.ui.R

class LayoutListFragment : ListBaseFragment() {
    override fun setData(): Array<String> = arrayOf(
        "RecyclerView",
        "SwipeRefreshLayout",
        "ConstraintLayout",
        "ViewPager2",
        "CardView"
    )

    override fun itemClickHandle(name: String) {
        @IdRes
        val resId: Int? = when (name) {
            "RecyclerView" -> R.id.action_lifecycleListFragment_to_recyclerViewFragment
            "ConstraintLayout" -> R.id.action_layoutListFragment_to_constraintLayoutFragment
            "ViewPager2" -> R.id.action_layoutListFragment_to_browseActivity
            "SwipeRefreshLayout" -> R.id.action_layoutListFragment_to_swipeRefreshActivity
            "CardView" -> R.id.action_layoutListFragment_to_cardViewFragment
            else -> null
        }
        resId?.let {
            findNavController().navigate(it)
        }
    }

}