package com.xxh.jetpacksample.room.example

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val uid: Int,
    @ColumnInfo("first_name") val firstName: String?,
    @ColumnInfo("last_name") val lastName: String?
)