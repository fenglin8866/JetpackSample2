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
package com.xxh.jetpacksample.room.codelab.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.xxh.jetpacksample.room.codelab.database.inventory.Item
import com.xxh.jetpacksample.room.codelab.database.inventory.ItemDao
import com.xxh.jetpacksample.room.codelab.database.schedule.Schedule
import com.xxh.jetpacksample.room.codelab.database.schedule.ScheduleDao

/**
 * Defines a database and specifies data tables that will be used.
 * Version is incremented as new tables/columns are added/removed/changed.
 * You can optionally use this class for one-time setup, such as pre-populating a database.
 */
@Database(entities = [Schedule::class, Item::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun scheduleDao(): ScheduleDao

    abstract fun itemDao(): ItemDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_database")
                    .createFromAsset("database/bus_schedule.db")
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                   // .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}
