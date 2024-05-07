package com.xxh.jetpacksample.ioc

import androidx.annotation.IdRes
import androidx.navigation.fragment.findNavController
import com.xxh.jetpacksample.R
import com.xxh.common.ListBaseFragment

class IocListFragment : ListBaseFragment() {
    override fun setData(): Array<String> = arrayOf(
        "Ioc",
        "DI",
        "Hilt之Logs",
        "Hilt之Login",
        "Dagger",
    )

    override fun itemClickHandle(name: String) {
        @IdRes
        val resId: Int? = when (name) {
            "Ioc" -> null
            "DI" -> null
            "Hilt之Logs" -> R.id.action_iocListFragment_to_buttonsFragment
            "Hilt之Login" -> R.id.action_iocListFragment_to_hiltLoginModuleFragment
            "Dagger" -> null
            else -> null
        }
        resId?.let {
            findNavController().navigate(it)
        }
    }

}