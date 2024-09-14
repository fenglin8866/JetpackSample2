package com.xxh.jetpacksample.navigation.fragment

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import com.xxh.jetpacksample.R
import com.xxh.jetpacksample.navigation.fragment.ui.login.LoginFragment

class NavigationFragmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_navigation_fragment)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initNavigation()
    }

    private fun initNavigation() {
        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        navController.graph = navController.createGraph(
            startDestination = Route.LIST_FRAGMENT
        ) {
            fragment<ItemListFragment>(Route.LIST_FRAGMENT) {

            }
            fragment<BlankFragment>(Route.BLANK_FRAGMENT) {

            }
            fragment<BlankViewModelFragment>(Route.VIEWMODEL_FRAGMENT) {

            }
            fragment<FullscreenFragment>(Route.FULLSCREEN_FRAGMENT) {

            }
            fragment<ScrollingFragment>(Route.SCROLLING_FRAGMENT) {

            }
            fragment<SettingsFragment>(Route.SETTINGS_FRAGMENT) {

            }
            fragment<ItemListDialogFragment>(Route.MODAL_FRAGMENT) {

            }
            fragment<LoginFragment>(Route.LOGIN_FRAGMENT) {

            }
        }
    }
}

