package com.xxh.jetpacksample.navigation.fragment

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.createGraph
import androidx.navigation.fragment.fragment
import androidx.navigation.navOptions
import com.xxh.jetpacksample.R
import com.xxh.jetpacksample.databinding.ActivityNavigationFragmentBinding
import com.xxh.jetpacksample.navigation.fragment.fragment.ScrollingFragment
import java.io.Serializable

class NavigationFragmentActivity : AppCompatActivity() {
    private lateinit var mBinding:ActivityNavigationFragmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding= ActivityNavigationFragmentBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
       // initNavigation()
    }

    /*private fun initNavigation() {
        val navController = findNavController(R.id.fragmentContainerView)
        navController.graph = navController.createGraph("main") {
            fragment<MainFragment>("main"){
                label="Main"
            }
            fragment<ScrollingFragment>("scrolling"){
                label="Scrolling"
                navOptions {
                    launchSingleTop=false
                    restoreState=true
                    anim {
                        popExit = R.anim.fade_in
                        popEnter = R.anim.fade_in
                        enter = R.anim.fade_in
                        exit = R.anim.fade_in
                    }
                    popUpTo(""){
                        inclusive=true
                        saveState=true
                    }
                }
            }
            fragment<FriendsListFragment>("friendsList"){
                label="Friends List"
            }
            fragment<ProfileFragment>("profile"){
                label="Profile"
            }
        }
    }*/

    private fun initNavigation2(navController: NavController) {
        navController.graph = navController.createGraph(Home) {
            fragment<ScrollingFragment, Home> {
                label = "Scrolling"
                navOptions {
                    launchSingleTop = false
                    restoreState = true
                    anim {
                        popExit = R.anim.fade_in
                        popEnter = R.anim.fade_in
                        enter = R.anim.fade_in
                        exit = R.anim.fade_in
                    }
                    popUpTo("") {
                        inclusive = true
                        saveState = true
                    }
                }
            }
        }
    }
}

data object Home : Serializable