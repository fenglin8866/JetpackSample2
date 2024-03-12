package com.xxh.jetpacksample.navigation.fragment.fragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.xxh.jetpacksample.R
import com.xxh.jetpacksample.databinding.FragmentMainNavigationBinding
import com.xxh.jetpacksample.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mBinding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        mBinding.friends.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_friendsListFragment)
        }
        return mBinding.root
    }
}