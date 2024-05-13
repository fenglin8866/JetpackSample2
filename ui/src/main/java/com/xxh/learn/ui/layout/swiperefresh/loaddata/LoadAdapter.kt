package com.xxh.learn.ui.layout.swiperefresh.loaddata

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xxh.learn.ui.R

class LoadAdapter(// 数据源
    private var datas: MutableList<String>, // 上下文Context
    private val context: Context, hasMore: Boolean
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder?>() {
    private val normalType = 0 // 第一种ViewType，正常的item
    private val footType = 1 // 第二种ViewType，底部的提示View

    private var hasMore = true // 变量，是否有更多数据

    // 暴露接口，改变fadeTips的方法
    var isFadeTips: Boolean = false // 变量，是否隐藏了底部的提示
        private set

    private val mHandler: Handler = Handler(Looper.getMainLooper()) //获取主线程的Handler

    init {
        // 初始化变量
        this.hasMore = hasMore
    }

    // 获取条目数量，之所以要加1是因为增加了一条footView
    override fun getItemCount(): Int {
        return datas.size + 1
    }

    // 自定义方法，获取列表中数据源的最后一个位置，比getItemCount少1，因为不计上footView
    fun getRealLastPosition(): Int {
        return datas.size
    }


    // 根据条目位置返回ViewType，以供onCreateViewHolder方法内获取不同的Holder
    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) {
            footType
        } else {
            normalType
        }
    }

    // 正常item的ViewHolder，用以缓存findView操作
    class NormalHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById<View>(R.id.textView) as TextView
    }

    // 底部footView的ViewHolder，用以缓存findView操作
    class FootHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tips: TextView =
            itemView.findViewById<View>(R.id.pull_to_refresh_loadmore_text) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // 根据返回的ViewType，绑定不同的布局文件，这里只有两种
        return if (viewType == normalType) {
            NormalHolder(LayoutInflater.from(context).inflate(R.layout.item_text_row, null))
        } else {
            FootHolder(LayoutInflater.from(context).inflate(R.layout.listview_footer, null))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // 如果是正常的item，直接设置TextView的值
        if (holder is NormalHolder) {
            holder.textView.text = datas[position]
        } else {
            // 之所以要设置可见，是因为我在没有更多数据时会隐藏了这个footView
            (holder as FootHolder).tips.visibility = View.VISIBLE
            // 只有获取数据为空时，hasMore为false，所以当我们拉到底部时基本都会首先显示“正在加载更多...”
            if (hasMore) {
                // 不隐藏footView提示
                isFadeTips = false
                if (datas.size > 0) {
                    // 如果查询数据发现增加之后，就显示正在加载更多
                    holder.tips.text = "正在加载更多..."
                }
            } else {
                if (datas.size > 0) {
                    // 如果查询数据发现并没有增加时，就显示没有更多数据了
                    holder.tips.text = "没有更多数据了"

                    // 然后通过延时加载模拟网络请求的时间，在500ms后执行
                    mHandler.postDelayed(object : Runnable {
                        override fun run() {
                            // 隐藏提示条
                            holder.tips.visibility = View.GONE
                            // 将fadeTips设置true
                            isFadeTips = true
                            // hasMore设为true是为了让再次拉到底时，会先显示正在加载更多
                            hasMore = true
                        }
                    }, 500)
                }
            }
        }
    }

    // 暴露接口，下拉刷新时，通过暴露方法将数据源置为空
    fun resetDatas() {
        datas = ArrayList()
    }

    // 暴露接口，更新数据源，并修改hasMore的值，如果有增加数据，hasMore为true，否则为false
    fun updateList(newDatas: List<String>?, hasMore: Boolean) {
        // 在原有的数据之上增加新数据
        if (newDatas != null) {
            datas.addAll(newDatas)
        }
        this.hasMore = hasMore
        notifyDataSetChanged()
    }
}
