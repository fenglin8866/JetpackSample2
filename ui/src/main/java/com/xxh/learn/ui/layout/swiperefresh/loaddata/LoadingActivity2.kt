package com.xxh.learn.ui.layout.swiperefresh.loaddata

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.xxh.learn.ui.R


class LoadingActivity2 : AppCompatActivity() {
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var list: MutableList<String>
    private lateinit var adapter: LoadAdapter
    private var lastVisibleItem = 0
    private val COUNT = 20
    private lateinit var mLayoutManager: LinearLayoutManager
    private val mHandler: Handler = Handler(Looper.getMainLooper())

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading_recyclerview2)
        initData() //制造假数据
        initView() //初始化布局
        initRefreshLayout() //初始化下拉刷新
        initRecyclerView() //显示recyclerview布局
    }

    private fun initData() {
        list = ArrayList()
        for (i in 1..60) {
            list.add("测试$i")
        }
    }

    private fun initView() {
        refreshLayout = findViewById<View>(R.id.refreshLayout) as SwipeRefreshLayout
        recyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView
    }

    private fun initRefreshLayout() {
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light)
        refreshLayout.setOnRefreshListener { //上拉刷新，加载第一页
            //refreshLayout.isRefreshing = true
            mHandler.postDelayed({
                adapter.resetDatas()
                updateRecyclerView(0, COUNT)
                refreshLayout.isRefreshing = false
            }, 1000)
        }
    }

    private fun initRecyclerView() {
        adapter = LoadAdapter(
            getDatas(0, COUNT),
            this,
            true
        )
        mLayoutManager = LinearLayoutManager(this)
        mLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = mLayoutManager
        recyclerView.adapter = adapter
        recyclerView.itemAnimator = DefaultItemAnimator()

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    lastVisibleItem = mLayoutManager.findLastVisibleItemPosition()
                     Log.d("xxh00", "lastVisibleItem=$lastVisibleItem")
                    if (!adapter.isFadeTips && lastVisibleItem + 1 == adapter.itemCount) {
                        mHandler.postDelayed({
                            updateRecyclerView(
                                adapter.getRealLastPosition(),
                                adapter.getRealLastPosition() + COUNT
                            )
                        }, 2000)
                    }
                    /*if (adapter.isFadeTips && lastVisibleItem + 2 == adapter.itemCount) {
                        mHandler.postDelayed({
                            updateRecyclerView(
                                adapter.getRealLastPosition(),
                                adapter.getRealLastPosition() + COUNT
                            )
                        }, 500)
                    }*/
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

            }
        })
    }

    private fun updateRecyclerView(fromIndex: Int, toIndex: Int) {
        val newDatas = getDatas(fromIndex, toIndex)
        if (newDatas.size > 0) {
            adapter.updateList(newDatas, true)
        } else {
            adapter.updateList(null, false)
        }
    }

    //拿到全部数据，加载
    private fun getDatas(firstIndex: Int, lastIndex: Int): MutableList<String> {
        val resList: MutableList<String> = ArrayList()
        for (i in firstIndex until lastIndex) {
            if (i < list.size) {
                resList.add(list[i])
            }
        }
        return resList
    }
}
