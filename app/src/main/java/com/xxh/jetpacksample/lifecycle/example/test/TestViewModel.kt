package com.xxh.jetpacksample.lifecycle.example.test

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle

class TestViewModel(
    application: Application,
    val handle: SavedStateHandle
) : AndroidViewModel(application) {

    private var mShowData: MutableLiveData<String?> = handle.getLiveData(QUERY, null)

    fun getShowData(): LiveData<String?> {
        return mShowData;
    }

    fun saveShowData(text: String) {
        mShowData.value = text
        handle[QUERY] = text
    }

    companion object {
        const val QUERY = "test_key"
    }
}