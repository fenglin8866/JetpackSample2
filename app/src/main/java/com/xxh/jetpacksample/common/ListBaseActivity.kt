package com.xxh.jetpacksample.common

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.xxh.jetpacksample.databinding.ActivityMainBinding


abstract class ListBaseActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val dataset = setData()
        val customAdapter = StringAdapter(dataset)
        customAdapter.setItemClickCallback {
            val intent: Intent? = setClickIntent(it)
            intent?.let {
                startActivity(intent)
            }
        }
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        mBinding.recyclerView.layoutManager = layoutManager
        mBinding.recyclerView.adapter = customAdapter
    }

    abstract fun setData(): Array<String>

    abstract fun setClickIntent(name: String): Intent?

}