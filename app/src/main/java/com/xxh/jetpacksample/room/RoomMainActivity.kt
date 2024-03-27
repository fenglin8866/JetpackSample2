package com.xxh.jetpacksample.room

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
import com.xxh.jetpacksample.navigation.codelab.navigation.NavigationCodelabMainActivity
import com.xxh.jetpacksample.navigation.codelab.wordsapp.WordMainActivity
import com.xxh.jetpacksample.navigation.example.donuttracker.NavigationMADSMainActivity
import com.xxh.jetpacksample.navigation.example.navigationadvancedsample.NavigationAdvancedMainActivity
import com.xxh.jetpacksample.navigation.example.navigationsample.NavigationSampleMainActivity
import com.xxh.jetpacksample.navigation.fragment.NavigationFragmentActivity
import com.xxh.jetpacksample.room.codelab.busschedule.BusScheduleActivity
import com.xxh.jetpacksample.room.codelab.inventory.InventoryActivity
import com.xxh.jetpacksample.room.codelab.words.WordsActivity

class RoomMainActivity : ListBaseActivity() {

    override fun setData(): Array<String> = arrayOf(
        "BusSchedule",
        "Inventory",
        "Words",
    )

    override fun setClickIntent(name: String): Intent? = when (name) {
        "BusSchedule" -> Intent(this, BusScheduleActivity::class.java)
        "Inventory" -> Intent(this, InventoryActivity::class.java)
        "Words" -> Intent(this, WordsActivity::class.java)
        else -> null
    }

}