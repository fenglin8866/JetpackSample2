package com.xxh.jetpacksample

import android.content.Intent
import com.example.android.architecture.blueprints.todoapp.tasks.TasksActivity
import com.google.samples.apps.sunflower.GardenActivity
import com.xxh.common.ListBaseActivity
import com.xxh.jetpacksample.ioc.IocMainActivity
import com.xxh.jetpacksample.navigation.NavigationMainActivity
import com.xxh.jetpacksample.room.RoomMainActivity
import com.xxh.learn.ui.UIMainActivity

class MainActivity : ListBaseActivity() {

    override fun setData(): Array<String> = arrayOf(
        "Lifecycle",
        "IOC",
        "Room",
        "Navigation",
        "SunFlower",
        "Todo",
        "UI"
    )

    override fun setClickIntent(name: String): Intent? = when (name) {
        "Lifecycle" -> Intent(this, MainActivity::class.java)
        "IOC" -> Intent(this, IocMainActivity::class.java)
        "Room" -> Intent(this, RoomMainActivity::class.java)
        "Navigation" -> Intent(this, NavigationMainActivity::class.java)
        "SunFlower" -> Intent(this, GardenActivity::class.java)
        "Todo" -> Intent(this, TasksActivity::class.java)
        "UI" -> Intent(this, UIMainActivity::class.java)
        else -> null
    }
}
