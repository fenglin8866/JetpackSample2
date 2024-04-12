package com.xxh.jetpacksample.lifecycle

import android.content.Intent
import androidx.annotation.IdRes
import androidx.navigation.fragment.findNavController
import com.xxh.jetpacksample.R
import com.xxh.jetpacksample.common.ListBaseFragment
import com.xxh.jetpacksample.lifecycle.example.persistence.ui.PersistenceActivity

class LifecycleListFragment : ListBaseFragment() {
    override fun setData(): Array<String> = arrayOf(
        "Persistence"
    )

    override fun itemClickHandle(name: String) {

         when (name) {
            "Persistence" -> startActivity(Intent(requireContext(),PersistenceActivity::class.java))
            else -> null
        }
        /*resId?.let {
            findNavController().navigate(it)
        }*/
    }

}