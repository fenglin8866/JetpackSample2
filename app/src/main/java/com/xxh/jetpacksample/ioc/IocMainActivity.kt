package com.xxh.jetpacksample.ioc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.xxh.jetpacksample.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IocMainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_ioc)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_ioc) as NavHostFragment
        navController = navHostFragment.navController
//        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}