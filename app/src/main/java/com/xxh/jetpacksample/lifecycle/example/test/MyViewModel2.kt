/*
package com.xxh.jetpacksample.lifecycle.example.test

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map

class MyViewModel2(
    private val coroutineScope: CloseableCoroutineScope = CloseableCoroutineScope()
) : ViewModel(coroutineScope) {
    // Other ViewModel logic ...

    val datas=MediatorLiveData<String>()
    val data1=MutableLiveData<String>()
    val data2=MutableLiveData<String>()
    val data3=MutableLiveData<Int>()
    fun test(){
        datas.addSource(data1){
            datas.value=it
        }
        datas.addSource(data2){
            datas.value=it
        }
        val data4 = data3.map {
            it.toString()
        }

    }
}*/
