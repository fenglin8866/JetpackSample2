/*
 * Copyright 2017, The Android Open Source Project
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
package com.xxh.jetpacksample.lifecycle.example.persistence.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.xxh.jetpacksample.JApplication
import com.xxh.jetpacksample.lifecycle.example.persistence.DataRepository
import com.xxh.jetpacksample.lifecycle.example.persistence.db.entity.ProductEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductListViewModel2(
    application: Application,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {
    private val mSavedStateHandler: SavedStateHandle
    private val mRepository: DataRepository

    /**
     * Expose the LiveData Products query so the UI can observe it.
     */
    //val products: LiveData<List<ProductEntity>>? = null

    val mProducts:LiveData<List<ProductEntity>>
    init {
        mSavedStateHandler = savedStateHandle
        mRepository = (application as JApplication).getProductRepository()

        // Use the savedStateHandle.getLiveData() as the input to switchMap,
        // allowing us to recalculate what LiveData to get from the DataRepository
        // based on what query the user has entered
        val liveData: LiveData<String?> = savedStateHandle.getLiveData(QUERY_KEY, null)
        /*mProducts = liveData.switchMap {
            if (it == null) {
                return@switchMap mRepository.products
            }
            return@switchMap mRepository.searchProducts("*${it}*")
        }*/
        mProducts=mRepository.products
    }

    fun setQuery(query: CharSequence?) {
        // Save the user's query into the SavedStateHandle.
        // This ensures that we retain the value across process death
        // and is used as the input into the Transformations.switchMap above
        mSavedStateHandler[QUERY_KEY] = query
    }

    fun insertAll(){
        viewModelScope.launch(Dispatchers.IO) {
            //mRepository.insert()
        }
    }

    companion object {
        private const val QUERY_KEY = "QUERY"
    }
}
