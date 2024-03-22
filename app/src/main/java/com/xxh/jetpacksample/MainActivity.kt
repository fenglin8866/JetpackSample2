package com.xxh.jetpacksample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.samples.apps.sunflower.GardenActivity
import com.xxh.jetpacksample.common.StringAdapter
import com.xxh.jetpacksample.databinding.ActivityMainBinding
import com.xxh.jetpacksample.ioc.dagger.main.DaggerMainActivity
import com.xxh.jetpacksample.ioc.example.ExampleActivity
import com.xxh.jetpacksample.ioc.hilt.ui.HiltMainActivity
import com.xxh.jetpacksample.navigation.NavigationMainActivity
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
            "Navigation"
        )
        val customAdapter = StringAdapter(dataset)
        customAdapter.setItemClickCallback {
            var intent: Intent? = null
            when (it) {
                "Example" -> Intent(this, ExampleActivity::class.java)
                "Hilt" -> intent = Intent(this, HiltMainActivity::class.java)
                "Dagger" -> intent = Intent(this, DaggerMainActivity::class.java)
                "Room" -> intent = Intent(this, RoomMainActivity::class.java)
                "SunFlower" -> intent = Intent(this, GardenActivity::class.java)
                "Navigation" -> intent = Intent(this, NavigationMainActivity::class.java)
            }
            intent?.let {
                startActivity(intent)
            }
        }
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        mBinding.recyclerView.layoutManager = layoutManager
        mBinding.recyclerView.adapter = customAdapter
    }
}
