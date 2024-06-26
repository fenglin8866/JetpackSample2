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
package com.xxh.jetpacksample.lifecycle.example.persistence.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.xxh.jetpacksample.lifecycle.example.persistence.db.entity.ProductEntity

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    fun loadAllProducts(): LiveData<List<ProductEntity?>?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(products: List<ProductEntity>)

    @Query("select * from products where id = :productId")
    fun loadProduct(productId: Int): LiveData<ProductEntity?>?

    @Query("select * from products where id = :productId")
    fun loadProductSync(productId: Int): ProductEntity?

    @Query(
        "SELECT products.* FROM products JOIN productsFts ON (products.id = productsFts.rowid) "
                + "WHERE productsFts MATCH :query"
    )
    fun searchAllProducts(query: String?): LiveData<List<ProductEntity?>?>?
}