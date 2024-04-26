package com.xxh.jetpacksample.bindings

import android.view.LayoutInflater
import com.xxh.jetpacksample.common.BaseActivity
import com.xxh.jetpacksample.databinding.ActivityBindingTestBinding

class BindingTestActivity : BaseActivity<ActivityBindingTestBinding>() {
    override fun bindView(inflater: LayoutInflater): ActivityBindingTestBinding {
       return ActivityBindingTestBinding.inflate(inflater)
    }

}