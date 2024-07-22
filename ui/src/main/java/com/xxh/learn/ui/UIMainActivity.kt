package com.xxh.learn.ui

import android.content.Intent
import com.xxh.common.ListBaseActivity
import com.xxh.learn.ui.component.StringTestActivity
import com.xxh.learn.ui.layout.LayoutActivity

class UIMainActivity : ListBaseActivity() {
    override fun setData(): Array<String> = arrayOf(
        "布局",
        "应用主题",
        "常用View",
        "文本和表情符号",
        "图形和视频",
        "动画和转场动效",
        "触摸和输入",
        "添加通知",
        "自定义启动",
    )

    override fun setClickIntent(name: String): Intent? = when (name) {
        "布局" -> Intent(this, LayoutActivity::class.java)
        "常用View" -> Intent(this, StringTestActivity::class.java)
        else -> null
    }
}