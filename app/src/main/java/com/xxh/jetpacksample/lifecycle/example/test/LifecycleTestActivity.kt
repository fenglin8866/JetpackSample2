package com.xxh.jetpacksample.lifecycle.example.test

import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.xxh.jetpacksample.common.BaseActivity
import com.xxh.jetpacksample.databinding.ActivityLifecycleTestBinding

class LifecycleTestActivity : BaseActivity<ActivityLifecycleTestBinding>() {

    private val testViewModel: TestViewModel by viewModels()

    override fun bindView(inflater: LayoutInflater): ActivityLifecycleTestBinding {
        return ActivityLifecycleTestBinding.inflate(inflater)
    }

    override fun initView() {
        super.initView()
        testViewModel.getShowData().observe(this) {
            mBinding.showText.text = it ?: "Hello"
        }
        mBinding.save.setOnClickListener {
            val text = mBinding.editText.text.toString()
            Log.e("xxh", text)
            testViewModel.saveShowData(text)
        }
    }
}