/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xxh.jetpacksample.ioc.hilt.codelab.login.registration.termsandconditions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.xxh.jetpacksample.R
import com.xxh.jetpacksample.ioc.hilt.codelab.login.registration.RegistrationViewModel
import com.xxh.jetpacksample.common.BaseFragment
import com.xxh.jetpacksample.databinding.FragmentTermsAndConditionsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TermsAndConditionsFragment : BaseFragment<FragmentTermsAndConditionsBinding>() {

    @Inject
    lateinit var registrationViewModel: RegistrationViewModel

    override fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTermsAndConditionsBinding {
        return FragmentTermsAndConditionsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.next.setOnClickListener{
            registrationViewModel.acceptTCs()
            onTermsAndConditionsAccepted()
        }
    }

    private fun onTermsAndConditionsAccepted() {
        registrationViewModel.registerUser()
        findNavController().navigate(R.id.action_termsAndConditionsFragment_to_hiltLoginModuleFragment)
    }
}
