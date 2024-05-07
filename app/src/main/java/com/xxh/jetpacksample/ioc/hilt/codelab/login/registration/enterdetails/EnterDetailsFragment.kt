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

package com.xxh.jetpacksample.ioc.hilt.codelab.login.registration.enterdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.xxh.jetpacksample.R
import com.xxh.jetpacksample.ioc.hilt.codelab.login.registration.RegistrationViewModel
import com.xxh.common.BaseFragment
import com.xxh.jetpacksample.databinding.FragmentEnterDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EnterDetailsFragment : BaseFragment<FragmentEnterDetailsBinding>() {

    /**
     * RegistrationViewModel is used to set the username and password information (attached to
     * Activity's lifecycle and shared between different fragments)
     * EnterDetailsViewModel is used to validate the user input (attached to this
     * Fragment's lifecycle)
     *
     * They could get combined but for the sake of the codelab, we're separating them so we have
     * different ViewModels with different lifecycles.
     */
    @Inject
    lateinit var registrationViewModel: RegistrationViewModel

    @Inject
    lateinit var enterDetailsViewModel: EnterDetailsViewModel

    private lateinit var errorTextView: TextView
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText

    override fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEnterDetailsBinding {
        return FragmentEnterDetailsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        enterDetailsViewModel.enterDetailsState.observe(
            viewLifecycleOwner
        ) { state ->
            when (state) {
                is EnterDetailsSuccess -> {
                    val username = usernameEditText.text.toString()
                    val password = passwordEditText.text.toString()
                    registrationViewModel.updateUserData(username, password)
                    findNavController().navigate(R.id.action_enterDetailsFragment_to_termsAndConditionsFragment)
                }

                is EnterDetailsError -> {
                    errorTextView.text = state.error
                    errorTextView.visibility = View.VISIBLE
                }
            }
        }

        setupViews()
    }

    private fun setupViews() {
        errorTextView = mBinding.error
        usernameEditText = mBinding.username
        usernameEditText.doOnTextChanged { _, _, _, _ -> errorTextView.visibility = View.INVISIBLE }
        passwordEditText = mBinding.password
        passwordEditText.doOnTextChanged { _, _, _, _ -> errorTextView.visibility = View.INVISIBLE }
        mBinding.next.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            enterDetailsViewModel.validateInput(username, password)
        }
    }
}

sealed class EnterDetailsViewState
object EnterDetailsSuccess : EnterDetailsViewState()
data class EnterDetailsError(val error: String) : EnterDetailsViewState()
