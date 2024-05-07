package com.xxh.jetpacksample.room

import androidx.annotation.IdRes
import androidx.navigation.fragment.findNavController
import com.xxh.jetpacksample.R
import com.xxh.common.ListBaseFragment

class RoomListFragment : ListBaseFragment(){
    override fun setData(): Array<String> = arrayOf(
        "BusSchedule",
        "Inventory",
        "Words",
    )

    override fun itemClickHandle(name: String) {
        @IdRes
        val resId: Int? = when (name) {
            "Words" -> R.id.nav_graph_room_word
            "BusSchedule" ->R.id.nav_graph_room_bus_schedule
            "Inventory" -> R.id.nav_graph_room_inventory
            else -> null
        }
        resId?.let {
            findNavController().navigate(it)
        }
    }

}