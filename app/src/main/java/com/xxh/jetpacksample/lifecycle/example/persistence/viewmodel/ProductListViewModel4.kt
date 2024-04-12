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
import androidx.lifecycle.switchMap
import com.xxh.jetpacksample.JApplication
import com.xxh.jetpacksample.lifecycle.example.persistence.DataRepository
import com.xxh.jetpacksample.lifecycle.example.persistence.db.entity.ProductEntity

class ProductListViewModel4(
    application: Application,
    private val mSavedStateHandler: SavedStateHandle
) : AndroidViewModel(application) {
    private val mRepository: DataRepository
    private val mProducts: LiveData<List<ProductEntity>>

    init {
        mRepository = (application as JApplication).getProductRepository()
        val liveData: LiveData<String?> = mSavedStateHandler.getLiveData(QUERY_KEY, null)
        mProducts = liveData.switchMap {
            if (it == null) {
                mRepository.products
            } else {
                mRepository.searchProducts("*$it*")
            }
        }
        /*if (liveData==null){
            liveData.switchMap{
                mRepository.searchProducts("*$it*")
            }.also { mProducts = it }
        }*/

        // Use the savedStateHandle.getLiveData() as the input to switchMap,
        // allowing us to recalculate what LiveData to get from the DataRepository
        // based on what query the user has entered
        /* mProducts = Transformations.switchMap(
                savedStateHandle.getLiveData("QUERY", null),
                (Function<CharSequence, LiveData<List<ProductEntity>>>) query -> {
                    if (TextUtils.isEmpty(query)) {
                        return mRepository.getProducts();
                    }
                    return mRepository.searchProducts("*" + query + "*");
                });*/
    }

    fun setQuery(query: CharSequence?) {
        // Save the user's query into the SavedStateHandle.
        // This ensures that we retain the value across process death
        // and is used as the input into the Transformations.switchMap above
        mSavedStateHandler[QUERY_KEY] = query
    }

    val products: LiveData<List<ProductEntity>>
        /**
         * Expose the LiveData Products query so the UI can observe it.
         */
        get() = mProducts

    companion object {
        private const val QUERY_KEY = "QUERY"
    }
}
