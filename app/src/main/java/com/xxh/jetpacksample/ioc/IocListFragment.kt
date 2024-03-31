package com.xxh.jetpacksample.ioc

import android.content.Intent
import androidx.annotation.IdRes
import androidx.navigation.fragment.findNavController
import com.xxh.jetpacksample.R
import com.xxh.jetpacksample.common.ListBaseFragment
import com.xxh.jetpacksample.ioc.hilt.codelab.login.main.DaggerMainActivity

class IocListFragment : ListBaseFragment(){
    override fun setData(): Array<String> = arrayOf(
        "Ioc",
        "DI",
        "Hilt之Logs",
        "Hilt之Login",
        "Dagger",
    )

    override fun itemClickHandle(name: String) {
        if(name=="Hilt之Login"){
            startActivity(Intent(requireContext(),DaggerMainActivity::class.java))
        }

        /*@IdRes
        val resId: Int? = when (name) {
            "Ioc" -> R.id.nav_graph_room_word
            "DI" ->R.id.nav_graph_room_bus_schedule
            "Hilt之Logs" -> R.id.action_iocListFragment_to_buttonsFragment
            "Hilt之Login" -> R.id.action_iocListFragment_to_buttonsFragment
            "Dagger" -> R.id.nav_graph_room_inventory
            else -> null
        }
        resId?.let {
            findNavController().navigate(it)
        }*/
    }

}