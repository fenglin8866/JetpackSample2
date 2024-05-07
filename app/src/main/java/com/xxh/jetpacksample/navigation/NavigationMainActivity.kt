package com.xxh.jetpacksample.navigation

import android.content.Intent
import com.xxh.common.ListBaseActivity
import com.xxh.jetpacksample.navigation.codelab.navigation.NavigationCodelabMainActivity
import com.xxh.jetpacksample.navigation.codelab.wordsapp.WordMainActivity
import com.xxh.jetpacksample.navigation.example.donuttracker.NavigationMADSMainActivity
import com.xxh.jetpacksample.navigation.example.navigationadvancedsample.NavigationAdvancedMainActivity
import com.xxh.jetpacksample.navigation.example.navigationsample.NavigationSampleMainActivity
import com.xxh.jetpacksample.navigation.fragment.NavigationFragmentActivity

class NavigationMainActivity : ListBaseActivity() {

    override fun setData(): Array<String> = arrayOf(
        "NavigationFragment",
        "NavigationSample",
        "NavigationAdvanced",
        "NavigationMADSkills",
        "NavigationCodelab",
        "NavigationWord",
    )

    override fun setClickIntent(name: String): Intent? = when (name) {
        "NavigationFragment" -> Intent(this, NavigationFragmentActivity::class.java)
        "NavigationSample" -> Intent(this, NavigationSampleMainActivity::class.java)
        "NavigationAdvanced" -> Intent(this, NavigationAdvancedMainActivity::class.java)
        "NavigationMADSkills" -> Intent(this, NavigationMADSMainActivity::class.java)
        "NavigationCodelab" -> Intent(this, NavigationCodelabMainActivity::class.java)
        "NavigationWord" -> Intent(this, WordMainActivity::class.java)
        else -> null
    }
}