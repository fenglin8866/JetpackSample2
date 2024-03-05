package com.xxh.jetpacksample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.xxh.jetpacksample.ioc.dagger.main.DaggerMainActivity
import com.xxh.jetpacksample.databinding.ActivityMainBinding
import com.xxh.jetpacksample.ioc.example.ExampleActivity
import com.xxh.jetpacksample.ioc.hilt.ui.HiltMainActivity
import com.xxh.jetpacksample.room.codelab.RoomMainActivity


class MainActivity : AppCompatActivity() {


    private lateinit var mBinding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val dataset = arrayOf("Example", "Hilt", "Dagger","Room")
        val customAdapter = CustomAdapter(dataset)
        customAdapter.setItemClickCallback {
            when (it) {
                0 -> startActivity(Intent(this, ExampleActivity::class.java))
                1 -> startActivity(Intent(this, HiltMainActivity::class.java))
                2 -> startActivity(Intent(this, DaggerMainActivity::class.java))
                3 -> startActivity(Intent(this, RoomMainActivity::class.java))
            }
        }

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        mBinding.recyclerView.layoutManager = layoutManager
        mBinding.recyclerView.adapter = customAdapter
    }
}
