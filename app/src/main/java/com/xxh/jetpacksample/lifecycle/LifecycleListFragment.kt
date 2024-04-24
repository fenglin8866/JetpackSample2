package com.xxh.jetpacksample.lifecycle

import android.content.Intent
import com.xxh.jetpacksample.common.ListBaseFragment
import com.xxh.jetpacksample.lifecycle.example.persistence.ui.PersistenceActivity
import com.xxh.jetpacksample.lifecycle.example.test.LifecycleTestActivity

class LifecycleListFragment : ListBaseFragment() {
    override fun setData(): Array<String> = arrayOf(
        "Persistence",
        "TestExample"
    )

    override fun itemClickHandle(name: String) {

         when (name) {
            "Persistence" -> startActivity(Intent(requireContext(),PersistenceActivity::class.java))
            "TestExample" -> startActivity(Intent(requireContext(), LifecycleTestActivity::class.java))
            else -> null
        }
        /*resId?.let {
            findNavController().navigate(it)
        }*/
    }

}