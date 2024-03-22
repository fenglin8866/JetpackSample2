package com.xxh.jetpacksample.navigation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.xxh.jetpacksample.common.StringAdapter
import com.xxh.jetpacksample.databinding.ActivityMainBinding
import com.xxh.jetpacksample.navigation.codelab.navigation.NavigationCodelabMainActivity
import com.xxh.jetpacksample.navigation.codelab.wordsapp.WordMainActivity
import com.xxh.jetpacksample.navigation.example.donuttracker.NavigationMADSMainActivity
import com.xxh.jetpacksample.navigation.example.navigationadvancedsample.NavigationAdvancedMainActivity
import com.xxh.jetpacksample.navigation.example.navigationsample.NavigationSampleMainActivity
import com.xxh.jetpacksample.navigation.fragment.NavigationFragmentActivity

class NavigationMainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        val dataset = arrayOf(
            "NavigationFragment",
            "NavigationSample",
            "NavigationAdvanced",
            "NavigationMADSkills",
            "NavigationCodelab",
            "NavigationWord",
        )
        val customAdapter = StringAdapter(dataset)
        customAdapter.setItemClickCallback {
            var intent: Intent? = null
            when (it) {
                "NavigationFragment" -> Intent(this, NavigationFragmentActivity::class.java)
                "NavigationSample" -> intent = Intent(this, NavigationSampleMainActivity::class.java)
                "NavigationAdvanced" -> intent = Intent(this, NavigationAdvancedMainActivity::class.java)
                "NavigationMADSkills" -> intent = Intent(this, NavigationMADSMainActivity::class.java)
                "NavigationCodelab" -> intent = Intent(this, NavigationCodelabMainActivity::class.java)
                "NavigationWord" -> intent = Intent(this, WordMainActivity::class.java)
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