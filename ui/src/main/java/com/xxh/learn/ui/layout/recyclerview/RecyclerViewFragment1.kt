package com.xxh.learn.ui.layout.recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.xxh.common.BaseFragment
import com.xxh.learn.ui.databinding.FragmentRecyclerView1Binding
import com.xxh.learn.ui.layout.data.News

class RecyclerViewFragment1 : BaseFragment<FragmentRecyclerView1Binding>() {
    override fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRecyclerView1Binding {
        return FragmentRecyclerView1Binding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val newsList = arrayListOf<News>()
        repeat(20) {
            newsList.add(News("aaa_${it}", "hello world"))
        }
        val adapter = NewsAdapter(newsList)
        mBinding.recyclerView1.layoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        mBinding.recyclerView1.adapter = adapter
    }

}