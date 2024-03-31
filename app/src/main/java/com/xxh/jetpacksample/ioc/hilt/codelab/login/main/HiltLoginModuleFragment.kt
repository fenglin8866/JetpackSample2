package com.xxh.jetpacksample.ioc.hilt.codelab.login.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.xxh.jetpacksample.R
import com.xxh.jetpacksample.common.BaseFragment
import com.xxh.jetpacksample.databinding.FragmentHiltLoginModuleBinding
import com.xxh.jetpacksample.ioc.hilt.codelab.login.user.UserManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HiltLoginModuleFragment : BaseFragment<FragmentHiltLoginModuleBinding>() {
    @Inject
    lateinit var mainViewModel: LoginMainViewModel

    @Inject
    lateinit var userManager: UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!userManager.isUserLoggedIn()) {
            if (!userManager.isUserRegistered()) {
                // startActivity(Intent(this, RegistrationActivity::class.java))
                findNavController().navigate(R.id.action_hiltLoginModuleFragment_to_enterDetailsFragment)
            } else {
                findNavController().navigate(R.id.action_hiltLoginModuleFragment_to_hiltLoginFragment)
                // startActivity(Intent(this, LoginActivity::class.java))
            }
        }
    }

    override fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHiltLoginModuleBinding {
        /*if (!userManager.isUserLoggedIn()) {
            if (!userManager.isUserRegistered()) {
                // startActivity(Intent(this, RegistrationActivity::class.java))
            } else {
                // startActivity(Intent(this, LoginActivity::class.java))
            }
        } */
        return FragmentHiltLoginModuleBinding.inflate(inflater, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        mBinding.hello.text = mainViewModel.welcomeText
        mBinding.settings.setOnClickListener {
            // startActivity(Intent(this, SettingsActivity::class.java))
            findNavController().navigate(R.id.action_hiltLoginModuleFragment_to_hiltSettingsFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        mBinding.notifications.text = mainViewModel.notificationsText
    }


}