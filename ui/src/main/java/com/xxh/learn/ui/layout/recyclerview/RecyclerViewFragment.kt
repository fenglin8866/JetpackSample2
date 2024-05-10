package com.xxh.learn.ui.layout.recyclerview

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.xxh.common.BaseFragment
import com.xxh.learn.ui.databinding.FragmentRecyclerViewBinding
import com.xxh.learn.ui.layout.recyclerview.recyclersample.flowerList.FlowersListActivity

class RecyclerViewFragment : BaseFragment<FragmentRecyclerViewBinding>() {
    private lateinit var demoCollectionAdapter: DemoCollectionAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    override fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRecyclerViewBinding {
        return FragmentRecyclerViewBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.flowerSample.setOnClickListener {
            startActivity(Intent(requireContext(), FlowersListActivity::class.java))
        }
        demoCollectionAdapter = DemoCollectionAdapter(this)
        viewPager = mBinding.pager;
        viewPager.adapter = demoCollectionAdapter
        tabLayout = mBinding.tabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = "OBJECT ${(position + 1)}"
        }.attach()
    }

}

class DemoCollectionAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        // Return a NEW fragment instance in createFragment(int).
        /*val fragment = DemoObjectFragment()
        fragment.arguments = Bundle().apply {
            // The object is just an integer.
            putInt(ARG_OBJECT, position + 1)
        }*/
        var fragment: Fragment = RecyclerViewFragment1()
        when (position) {
            1 -> fragment = RecyclerViewFragment2()
            2 -> fragment = RecyclerViewFragment3()
        }
        return fragment
    }
}

//09:51:57.517  D  [xxh00]getStatusBarHeight1=99 statusBarHeight=111 statusBarH=150


//10:09:58.502  D  [xxh00]getStatusBarHeight1=100 statusBarHeight=74 statusBarH=74