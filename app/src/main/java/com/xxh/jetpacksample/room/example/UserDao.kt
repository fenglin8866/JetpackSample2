package com.xxh.jetpacksample.room.example

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Query("select * from user")
    fun getAllUser():List<User>

    @Insert
    fun insertAll(vararg users:User)

    @Delete
    fun deleteUser(user:User)
}