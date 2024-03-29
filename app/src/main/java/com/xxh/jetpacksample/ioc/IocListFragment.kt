package com.xxh.jetpacksample.ioc

import androidx.annotation.IdRes
import androidx.navigation.fragment.findNavController
import com.xxh.jetpacksample.R
import com.xxh.jetpacksample.common.ListBaseFragment

class IocListFragment : ListBaseFragment(){
    override fun setData(): Array<String> = arrayOf(
        "Ioc",
        "DI",
        "Hilt",
        "Dagger",
    )

    override fun itemClickHandle(name: String) {
        @IdRes
        val resId: Int? = when (name) {
            "Ioc" -> R.id.nav_graph_room_word
            "DI" ->R.id.nav_graph_room_bus_schedule
            "Hilt" -> R.id.nav_graph_room_inventory
            "Dagger" -> R.id.nav_graph_room_inventory
            else -> null
        }
        resId?.let {
            findNavController().navigate(it)
        }
    }

}