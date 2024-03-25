package com.xxh.jetpacksample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.samples.apps.sunflower.GardenActivity
import com.xxh.jetpacksample.common.ListBaseActivity
import com.xxh.jetpacksample.common.StringAdapter
import com.xxh.jetpacksample.databinding.ActivityMainBinding
import com.xxh.jetpacksample.ioc.dagger.main.DaggerMainActivity
import com.xxh.jetpacksample.ioc.example.ExampleActivity
import com.xxh.jetpacksample.ioc.hilt.ui.HiltMainActivity
import com.xxh.jetpacksample.navigation.NavigationMainActivity
import com.xxh.jetpacksample.room.RoomMainActivity
import com.xxh.jetpacksample.room.codelab.words.WordsActivity

class MainActivity : ListBaseActivity() {

    override fun setData(): Array<String> = arrayOf(
        "Example",
        "Hilt",
        "Dagger",
        "Room",
        "SunFlower",
        "Navigation"
    )

    override fun setClickIntent(name: String): Intent? = when (name) {
        "Example" -> Intent(this, ExampleActivity::class.java)
        "Hilt" -> Intent(this, HiltMainActivity::class.java)
        "Dagger" -> Intent(this, DaggerMainActivity::class.java)
        "Room" -> Intent(this, RoomMainActivity::class.java)
        "SunFlower" -> Intent(this, GardenActivity::class.java)
        "Navigation" -> Intent(this, NavigationMainActivity::class.java)
        else -> null
    }
}
