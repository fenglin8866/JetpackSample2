package com.xxh.learn.ui.layout.swiperefresh.loaddata

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.xxh.learn.ui.R
import java.util.Date


class LoadingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_loading_recyclerview)

        // 模拟一些数据
        val datas: MutableList<String> = ArrayList()
        for (i in 0..19) {
            datas.add("item - $i")
        }

        // 构造适配器
        val adapter: BaseAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            datas
        )
        // 获取listview实例
        val listView = findViewById<View>(R.id.listview) as ListView
        listView.adapter = adapter

        // 获取RefreshLayout实例
        val myRefreshListView = findViewById<View>(R.id.refresh_layout) as RefreshLayout

        // 设置下拉刷新时的颜色值,颜色值需要定义在xml中
        //设置下拉进度条的颜色主题，参数为可变参数，并且是资源id，可以设置多种不同的颜色，每转一圈就显示一种颜色。
        myRefreshListView.setColorScheme(
            R.color.swipe_color_1, R.color.swipe_color_2,
            R.color.swipe_color_3, R.color.swipe_color_4
        )

        // 设置下拉进度的背景颜色，默认就是白色的
        myRefreshListView.setProgressBackgroundColorSchemeResource(android.R.color.white);
        // 设置下拉刷新监听器
        myRefreshListView.setOnRefreshListener {
            Toast.makeText(this, "refresh", Toast.LENGTH_SHORT).show()
            myRefreshListView.postDelayed({ // 更新数据
                datas.add(0,Date().toGMTString())
                adapter.notifyDataSetChanged()
                // 更新完后调用该方法结束刷新,设置刷新状态，true表示正在刷新，false表示取消刷新。
                myRefreshListView.isRefreshing = false
            }, 1000)
        }

        // 加载监听器
        myRefreshListView.setOnLoadListener {
            Toast.makeText(this, "load", Toast.LENGTH_SHORT).show()
            myRefreshListView.postDelayed({
                datas.add(Date().toGMTString())
                adapter.notifyDataSetChanged()
                // 加载完后调用该方法
                myRefreshListView.setLoading(false)
            }, 1500)
        }
    }
}