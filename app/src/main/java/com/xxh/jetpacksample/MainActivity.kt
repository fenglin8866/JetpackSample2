package com.xxh.jetpacksample

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import com.google.samples.apps.sunflower.GardenActivity
import com.xxh.jetpacksample.common.ListBaseActivity
import com.xxh.jetpacksample.ioc.IocMainActivity
import com.xxh.jetpacksample.lifecycle.LifecycleMainActivity
import com.xxh.jetpacksample.navigation.NavigationMainActivity
import com.xxh.jetpacksample.room.RoomMainActivity

class MainActivity : ListBaseActivity() {

    override fun setData(): Array<String> = arrayOf(
        "Lifecycle",
        "IOC",
        "Room",
        "Navigation",
        "SunFlower"
    )

    override fun setClickIntent(name: String): Intent? = when (name) {
        "Lifecycle" -> Intent(this, LifecycleMainActivity::class.java)
        "IOC" -> Intent(this, IocMainActivity::class.java)
        "Room" -> Intent(this, RoomMainActivity::class.java)
        "Navigation" -> Intent(this, NavigationMainActivity::class.java)
        "SunFlower" -> Intent(this, GardenActivity::class.java)
        else -> null
    }
}
