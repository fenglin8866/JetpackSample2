package com.xxh.learn.ui.layout.swiperefresh

import android.content.Intent
import com.xxh.common.ListBaseActivity
import com.xxh.learn.ui.layout.swiperefresh.loaddata.LoadingActivity

class SwipeRefreshListActivity : ListBaseActivity() {
    override fun setData(): Array<String> = arrayOf(
        "SwipeRefresh",
        "Loading",
    )

    override fun setClickIntent(name: String): Intent? = when (name) {
        "SwipeRefresh" -> Intent(this, SwipeRefreshActivity::class.java)
        "Loading" -> Intent(this, LoadingActivity::class.java)
        else -> null
    }
}