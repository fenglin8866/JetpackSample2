/*
 * Copyright (C) 2021 The Android Open Source Project
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
package com.xxh.jetpacksample.room.codelab.busschedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xxh.jetpacksample.JApplication
import com.xxh.jetpacksample.common.BaseFragment
import com.xxh.jetpacksample.room.codelab.busschedule.viewmodels.BusScheduleViewModel
import com.xxh.jetpacksample.room.codelab.busschedule.viewmodels.BusScheduleViewModelFactory
import com.xxh.jetpacksample.databinding.StopScheduleFragmentBinding
import kotlinx.coroutines.launch

class StopScheduleFragment : BaseFragment<StopScheduleFragmentBinding>() {

    companion object {
        var STOP_NAME = "stopName"
    }

    private lateinit var recyclerView: RecyclerView

    private lateinit var stopName: String

    private val viewModel: BusScheduleViewModel by activityViewModels {
        BusScheduleViewModelFactory(
            (activity?.application as JApplication).databaseApp.scheduleDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            stopName = it.getString(STOP_NAME).toString()
        }
    }

    override fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): StopScheduleFragmentBinding {
        return StopScheduleFragmentBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = mBinding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val busStopAdapter = BusStopAdapter {}
        // by passing in the stop name, filtered results are returned,
        // and tapping rows won't trigger navigation
        recyclerView.adapter = busStopAdapter
        lifecycle.coroutineScope.launch {
            viewModel.scheduleForStopName(stopName).collect {
                busStopAdapter.submitList(it)
            }
        }
    }
}
