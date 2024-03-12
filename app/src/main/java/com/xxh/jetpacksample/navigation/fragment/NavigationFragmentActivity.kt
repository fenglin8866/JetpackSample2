package com.xxh.jetpacksample.navigation.fragment

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.xxh.jetpacksample.R
import com.xxh.jetpacksample.databinding.ActivityNavigationFragmentBinding

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
            }
            fragment<FriendsListFragment>("friendsList"){
                label="Friends List"
            }
            fragment<ProfileFragment>("profile"){
                label="Profile"
            }
        }
    }*/
}