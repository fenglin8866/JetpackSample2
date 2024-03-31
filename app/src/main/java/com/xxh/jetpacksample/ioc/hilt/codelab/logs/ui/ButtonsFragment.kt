/*
 * Copyright (C) 2020 The Android Open Source Project
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

package com.xxh.jetpacksample.ioc.hilt.codelab.logs.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.xxh.jetpacksample.R
import com.xxh.jetpacksample.common.BaseFragment
import com.xxh.jetpacksample.databinding.FragmentButtonsBinding
import com.xxh.jetpacksample.ioc.hilt.codelab.logs.data.LoggerDataSource
import com.xxh.jetpacksample.ioc.hilt.codelab.logs.di.InMemoryLogger
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Fragment that displays buttons whose interactions are recorded.
 */
@AndroidEntryPoint
class ButtonsFragment : BaseFragment<FragmentButtonsBinding>() {
    @InMemoryLogger
    @Inject
    lateinit var logger: LoggerDataSource

    override fun bindView(inflater: LayoutInflater, container: ViewGroup?): FragmentButtonsBinding {
        return FragmentButtonsBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       mBinding.button1.setOnClickListener {
            logger.addLog("Interaction with 'Button 1'")
        }

        mBinding.button2.setOnClickListener {
            logger.addLog("Interaction with 'Button 2'")
        }

        mBinding.button3.setOnClickListener {
            logger.addLog("Interaction with 'Button 3'")
        }

        mBinding.allLogs.setOnClickListener {
            //navigator.navigateTo(Screens.LOGS)
            findNavController().navigate(R.id.action_buttonsFragment_to_logsFragment)
        }

        mBinding.deleteLogs.setOnClickListener {
            logger.removeLogs()
        }
    }
}
