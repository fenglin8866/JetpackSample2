package com.xxh.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.xxh.common.databinding.ContentListBinding


abstract class ListBaseFragment : BaseFragment<ContentListBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataset = setData()
        val customAdapter = StringAdapter(dataset)
        customAdapter.setItemClickCallback {
            itemClickHandle(it)
        }
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        mBinding.recyclerView.layoutManager = layoutManager
        mBinding.recyclerView.adapter = customAdapter
    }

    override fun bindView(inflater: LayoutInflater, container: ViewGroup?): ContentListBinding {
        return ContentListBinding.inflate(inflater, container, false)
    }

    abstract fun setData(): Array<String>

    abstract fun itemClickHandle(name: String)

}