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

package com.xxh.jetpacksample.ioc.hilt.codelab.login.user

import com.xxh.jetpacksample.ioc.hilt.codelab.login.storage.Storage

private const val REGISTERED_USER = "registered_user"
private const val PASSWORD_SUFFIX = "password"

/**
 * Handles User lifecycle. Manages registrations, logs in and logs out.
 * Knows when the user is logged in.
 * 处理用户生命周期。管理注册、登录和退出。知道用户何时登录。
 *
 * 用于ViewModel处理
 */
class UserManager(private val storage: Storage) {

    /**
     *  UserDataRepository is specific to a logged in user. This determines if the user
     *  is logged in or not, when the user logs in, a new instance will be created.
     *  When the user logs out, this will be null.
     *  UserDataRepository 特定于登录用户。这决定了用户是否登录，当用户登录时，将创建一个新实例。当用户注销时，这将为 null。
     */
    var userDataRepository: UserDataRepository? = null

    val username: String
        get() = storage.getString(REGISTERED_USER)

    /**
     * 是否登录
     */
    fun isUserLoggedIn() = userDataRepository != null

    /**
     * 是否注册
     */
    fun isUserRegistered() = storage.getString(REGISTERED_USER).isNotEmpty()

    /**
     * 注册操作
     */
    fun registerUser(username: String, password: String) {
        storage.setString(REGISTERED_USER, username)
        storage.setString("$username$PASSWORD_SUFFIX", password)
        userJustLoggedIn()
    }

    /**
     * 登录操作
     */
    fun loginUser(username: String, password: String): Boolean {
        val registeredUser = this.username
        if (registeredUser != username) return false

        val registeredPassword = storage.getString("$username$PASSWORD_SUFFIX")
        if (registeredPassword != password) return false

        userJustLoggedIn()
        return true
    }

    /**
     * 退出操作
     */
    fun logout() {
        userDataRepository = null
    }

    /**
     * 未登录操作
     */
    fun unregister() {
        val username = storage.getString(REGISTERED_USER)
        storage.setString(REGISTERED_USER, "")
        storage.setString("$username$PASSWORD_SUFFIX", "")
        logout()
    }

    private fun userJustLoggedIn() {
        userDataRepository = UserDataRepository(this)
    }
}
