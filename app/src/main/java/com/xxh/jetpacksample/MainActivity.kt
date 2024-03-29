package com.xxh.jetpacksample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xxh.jetpacksample.databinding.ActivityMainBinding
import com.xxh.jetpacksample.hilt.ui.HiltMainActivity


class MainActivity : AppCompatActivity() {


    private lateinit var mBinding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mBinding.exampleDemo.setOnClickListener {
        }
        mBinding.hiltDemo.setOnClickListener {
            startActivity(Intent(this,HiltMainActivity::class.java))
        }

    }
}