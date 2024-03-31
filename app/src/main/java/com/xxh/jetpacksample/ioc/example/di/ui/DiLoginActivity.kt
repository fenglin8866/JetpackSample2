package com.xxh.jetpacksample.ioc.example.di.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xxh.jetpacksample.JApplication
import com.xxh.jetpacksample.R
import com.xxh.jetpacksample.ioc.example.di.AppContainer
import com.xxh.jetpacksample.ioc.example.di.data.LoginUserData

class DiLoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var loginData: LoginUserData
    private lateinit var appContainer: AppContainer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_di_login)
        /**
         *第一版手动依赖注入
         * 存在的问题：
         * 1、重复的样板代码
         * 2、复用困难，不易测试
         * 3、必须按顺序声明依赖项
         */
        /* val userLocalDataSource = UserLocalDataSource()
         val loginRetrofitService = LoginRetrofitService()
         val userRemoteDataSource = UserRemoteDataSource(loginRetrofitService)
         val userRepository = UserRepository(userLocalDataSource, userRemoteDataSource)
         val viewModel=LoginViewModel(userRepository)*/

        /**
         *第二版手动依赖注入
         * 1、减少样板代码
         * 2、处理repository非单例复用问题
         *
         * 1、必须自行管理AppContainer
         * 2、大量样板代码，需要手动创建工厂或参数
         * 3、没有限定作用域
         */
        /* val appContainer = (application as JApplication).appContainer
         val viewModel = appContainer.loginViewModelFactory.create()*/
        /**
         *第三版手动依赖注入
         * 控制LoginContainer作用域
         */
        appContainer = (application as JApplication).appContainer
        loginViewModel = appContainer.loginContainer?.loginViewModelFactory!!.create()
        loginData = appContainer.loginContainer?.loginUserData!!

    }

    override fun onDestroy() {
        super.onDestroy()
        appContainer.loginContainer = null
    }
}