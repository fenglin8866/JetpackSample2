package com.xxh.jetpacksample.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xxh.jetpacksample.R
import com.xxh.jetpacksample.databinding.ActivityExampleBinding
import com.xxh.jetpacksample.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import javax.inject.Inject

@AndroidEntryPoint
class ExampleActivity : AppCompatActivity() {

    @Inject
    lateinit var analytics: AnalyticsAdapter

    private lateinit var mBinding: ActivityExampleBinding

    @AuthInterceptorOkHttpClient
    @Inject
    lateinit var okHttpClient: OkHttpClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityExampleBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mBinding.tv.text = analytics.input()
        mBinding.button.setOnClickListener {
            mBinding.tv.text = analytics.analytics()
        }

    }
}