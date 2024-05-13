package com.xxh.learn.ui.layout.swiperefresh.loaddata

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.AbsListView
import android.widget.ListView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.xxh.learn.ui.R

/**
 * 继承自SwipeRefreshLayout,从而实现滑动到底部时上拉加载更多的功能.
 *
 * @author mrsimple
 */
class    RefreshLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    SwipeRefreshLayout(
        context, attrs
    ) {
    /**
     * 滑动到最下面时的上拉操作
     */
    private val mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop

    /**
     * listview实例
     */
    private var mListView: ListView? = null
    private var mRecyclerView: RecyclerView? = null

    /**
     * 上拉监听器, 到了最底部的上拉加载操作
     */
    private var mOnLoadListener: OnLoadListener? = null

    /**
     * ListView的加载中footer
     */
    private val mListViewFooter: View = LayoutInflater.from(context).inflate(
        R.layout.listview_footer, null, false
    )

    /**
     * 按下时的y坐标
     */
    private var mYDown = 0

    /**
     * 抬起时的y坐标, 与mYDown一起用于滑动到底部时判断是上拉还是下拉
     */
    private var mLastY = 0

    /**
     * 是否在加载中 ( 上拉加载更多 )
     */
    private var isLoading = false

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        // 初始化ListView对象
        if (mListView == null || mRecyclerView == null) {
            getListView()
        }
    }

    /**
     * 获取ListView对象
     */
    private fun getListView() {
        val children = childCount
        if (children > 0) {
            when (val childView = getChildAt(0)) {
                is ListView -> {
                    mListView = childView
                    // 设置滚动监听器给ListView, 使得滚动的情况下也可以自动加载
                    mListView?.setOnScrollListener(object : AbsListView.OnScrollListener {
                        /**
                         * ListView的滚动监听
                         * 在滚动列表视图或网格视图时调用的回调方法。如果正在滚动视图，则在呈现滚动的下一帧之前将调用此方法。
                         * 特别是，它将在调用 {@link AdaptergetView（int， View， ViewGroup）} 之前调用。
                         * 一次滚动回调两次（开始和结束），onScroll在onScrollStateChanged之间
                         * scrollState 当前滚动状态。{@link SCROLL_STATE_TOUCH_SCROLL} 或 {@link SCROLL_STATE_IDLE} 之一。
                         */
                        override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
                            // 移动过程中判断时候能下拉加载更多
                            if (canLoad()) {
                                // 加载数据
                                loadData();
                            }
                            Log.d(
                                "xxh0511",
                                "onScrollStateChanged view=$view scrollState=$scrollState"
                            )
                        }

                        /**
                         * ListView的滚动监听
                         * 滚动列表或网格时要调用的回调方法。这将在滚动完成后调用
                         * 回调比较频繁
                         * 第一个可见单元格的索引（如果 visibleItemCount == 0，则忽略）
                         */
                        override fun onScroll(
                            view: AbsListView, firstVisibleItem: Int, visibleItemCount: Int,
                            totalItemCount: Int
                        ) {
                            /*// 滚动时到了最底部也可以加载更多
                            if (canLoad()) {
                                loadData()
                            }*/
                            Log.d(
                                "xxh0511",
                                "onScroll view=$view firstVisibleItem=$firstVisibleItem visibleItemCount=${visibleItemCount} totalItemCount=${totalItemCount}"
                            )
                        }

                    })
                    Log.d(VIEW_LOG_TAG, "### 找到listview")
                }

                is RecyclerView -> {
                    mRecyclerView = childView
                    mRecyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                        override fun onScrollStateChanged(
                            recyclerView: RecyclerView,
                            newState: Int
                        ) {
                            if (canLoad()) {
                                // 加载数据
                                loadData();
                            }
                        }
                    })
                }
            }
        }
    }


    /*
     * (non-Javadoc)
     * @see android.view.ViewGroup#dispatchTouchEvent(android.view.MotionEvent)
     * switch (ev.getAction()) {
          case MotionEvent.ACTION_DOWN:
              // 移动的起点
              mDownY = ev.getY();
              break;
          case MotionEvent.ACTION_MOVE:
              // 移动过程中判断时候能下拉加载更多
              if (canLoadMore()) {
                  // 加载数据
                  loadData();
              }
              break;
          case MotionEvent.ACTION_UP:
              // 移动的终点
              mUpY = getY();
              break;
      }
      return super.dispatchTouchEvent(ev);
  }
     */
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val action = event.action

        when (action) {
            MotionEvent.ACTION_DOWN ->     // 按下
                mYDown = event.rawY.toInt()

            MotionEvent.ACTION_MOVE -> { // 移动
                //缺点：调用太过频繁，影响性能，优点：滑动时能调用加载，而不需要等完成滑动
                /*if (canLoad()) {
                    // 加载数据
                    loadData();
                }*/
            }

            MotionEvent.ACTION_UP -> {  // 抬起
                mLastY = event.rawY.toInt()
                //移动过程中判断时候能下拉加载更多
                if (canLoad()) {
                    loadData()
                }
            }


            else -> {}
        }
        return super.dispatchTouchEvent(event)
    }

    /**
     * 是否可以加载更多, 条件是到了最底部, listview不在加载中, 且为上拉操作.
     *
     * @return
     */
    private fun canLoad(): Boolean {
        return isBottom && !isLoading && isPullUp
    }

    private val isBottom: Boolean
        /**
         * 判断是否到了最底部
         */
        get() {
            if (mListView != null && mListView!!.adapter != null) {
                return mListView!!.lastVisiblePosition == (mListView!!.adapter.count - 1)
            }
            return false
        }

    private val isPullUp: Boolean
        /**
         * 是否是上拉操作
         *
         * @return
         */
        get() = (mYDown - mLastY) >= mTouchSlop

    /**
     * 如果到了最底部,而且是上拉操作.那么执行onLoad方法
     */
    private fun loadData() {
        // 设置状态
        setLoading(true)
        mOnLoadListener?.onLoad()
    }

    /**
     * @param loading
     */
    internal fun setLoading(loading: Boolean) {
        isLoading = loading
        if (isLoading) {
            mListView?.addFooterView(mListViewFooter)
        } else {
            mListView?.removeFooterView(mListViewFooter)
            mYDown = 0
            mLastY = 0
        }
    }

    /**
     * @param loadListener
     */
    fun setOnLoadListener(loadListener: OnLoadListener?) {
        mOnLoadListener = loadListener
    }

    /**
     * 加载更多的监听器
     *
     * @author mrsimple
     */
    fun interface OnLoadListener {
        fun onLoad()
    }
}
