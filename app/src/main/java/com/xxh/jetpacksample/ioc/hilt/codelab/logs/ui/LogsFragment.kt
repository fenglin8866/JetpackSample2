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

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xxh.jetpacksample.ioc.hilt.codelab.logs.data.Log
import com.xxh.jetpacksample.ioc.hilt.codelab.logs.util.DateFormatter
import com.xxh.jetpacksample.R
import com.xxh.jetpacksample.common.BaseFragment
import com.xxh.jetpacksample.databinding.FragmentLogsBinding
import com.xxh.jetpacksample.ioc.hilt.codelab.logs.data.LoggerDataSource
import com.xxh.jetpacksample.ioc.hilt.codelab.logs.di.InMemoryLogger
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Fragment that displays the database logs.
 */
@AndroidEntryPoint
class LogsFragment : BaseFragment<FragmentLogsBinding>() {
    @InMemoryLogger
    @Inject
    lateinit var logger: LoggerDataSource

    @Inject
    lateinit var dateFormatter: DateFormatter

    override fun bindView(inflater: LayoutInflater, container: ViewGroup?): FragmentLogsBinding {
        return FragmentLogsBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mBinding.recyclerView.apply {
            setHasFixedSize(true)
        }
    }

    override fun onResume() {
        super.onResume()

        logger.getAllLogs { logs ->
            mBinding.recyclerView.adapter =
                LogsViewAdapter(
                    logs,
                    dateFormatter
                )
        }
    }
}

/**
 * RecyclerView adapter for the logs list.
 */
private class LogsViewAdapter(
    private val logsDataSet: List<Log>,
    private val daterFormatter: DateFormatter
) : RecyclerView.Adapter<LogsViewAdapter.LogsViewHolder>() {

    class LogsViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogsViewHolder {
        return LogsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.text_row_item, parent, false) as TextView
        )
    }

    override fun getItemCount(): Int {
        return logsDataSet.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: LogsViewHolder, position: Int) {
        val log = logsDataSet[position]
        holder.textView.text = "${log.msg}\n\t${daterFormatter.formatDate(log.timestamp)}"
    }
}
