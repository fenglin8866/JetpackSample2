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

package com.xxh.jetpacksample.hilt

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.room.Room
import com.xxh.jetpacksample.hilt.data.AppDatabase
import com.xxh.jetpacksample.hilt.data.LoggerLocalDataSource
import com.xxh.jetpacksample.hilt.navigator.AppNavigator
import com.xxh.jetpacksample.hilt.navigator.AppNavigatorImpl
import com.xxh.jetpacksample.hilt.util.DateFormatter

class ServiceLocator(applicationContext: Context) {

    private val logsDatabase = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java,
        "logging.db"
    ).build()

    val loggerLocalDataSource = LoggerLocalDataSource(logsDatabase.logDao())

    fun provideDateFormatter() = DateFormatter()

    fun provideNavigator(activity: FragmentActivity): AppNavigator {
        return AppNavigatorImpl(activity)
    }
}
