package com.xxh.jetpacksample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.samples.apps.sunflower.GardenActivity
import com.xxh.jetpacksample.ioc.dagger.main.DaggerMainActivity
import com.xxh.jetpacksample.databinding.ActivityMainBinding
import com.xxh.jetpacksample.ioc.example.ExampleActivity
import com.xxh.jetpacksample.ioc.hilt.ui.HiltMainActivity
import com.xxh.jetpacksample.navigation.compose.NavigationComposeActivity
import com.xxh.jetpacksample.navigation.example.donuttracker.NavigationMADSMainActivity
import com.xxh.jetpacksample.navigation.example.navigationadvancedsample.NavigationAdvancedMainActivity
import com.xxh.jetpacksample.navigation.example.navigationsample.NavigationSampleMainActivity
import com.xxh.jetpacksample.navigation.fragment.NavigationFragmentActivity
import com.xxh.jetpacksample.room.codelab.RoomMainActivity


class MainActivity : AppCompatActivity() {


    private lateinit var mBinding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val dataset = arrayOf(
            "Example",
            "Hilt",
            "Dagger",
            "Room",
            "SunFlower",
            "NavigationCompose",
            "NavigationFragment",
            "NavigationSample",
            "NavigationAdvanced",
            "NavigationMADSkills",
        )
        val customAdapter = CustomAdapter(dataset)
        customAdapter.setItemClickCallback {
            when (it) {
                0 -> startActivity(Intent(this, ExampleActivity::class.java))
                1 -> startActivity(Intent(this, HiltMainActivity::class.java))
                2 -> startActivity(Intent(this, DaggerMainActivity::class.java))
                3 -> startActivity(Intent(this, RoomMainActivity::class.java))
                4 -> startActivity(Intent(this, GardenActivity::class.java))
                5 -> startActivity(Intent(this, NavigationComposeActivity::class.java))
                6 -> startActivity(Intent(this, NavigationFragmentActivity::class.java))
                7 -> startActivity(Intent(this, NavigationSampleMainActivity::class.java))
                8 -> startActivity(Intent(this, NavigationAdvancedMainActivity::class.java))
                9 -> startActivity(Intent(this, NavigationMADSMainActivity::class.java))
            }
        }

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        mBinding.recyclerView.layoutManager = layoutManager
        mBinding.recyclerView.adapter = customAdapter
    }
}
