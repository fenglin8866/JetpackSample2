package com.xxh.learn.ui.layout.recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.xxh.common.BaseFragment
import com.xxh.learn.ui.databinding.FragmentRecyclerView1Binding
import com.xxh.learn.ui.layout.data.Datasource

class RecyclerViewFragment2 : BaseFragment<FragmentRecyclerView1Binding>() {
    override fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRecyclerView1Binding {
        return FragmentRecyclerView1Binding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize data.
        val myDataset = Datasource().loadAffirmations()

        val recyclerView = mBinding.recyclerView1
        recyclerView.layoutManager=LinearLayoutManager(requireContext())
        recyclerView.adapter = AffirmationAdapter(requireContext(), myDataset)

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true)
    }


}