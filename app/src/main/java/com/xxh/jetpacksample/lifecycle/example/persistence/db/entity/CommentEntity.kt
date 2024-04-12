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
package com.xxh.jetpacksample.lifecycle.example.persistence.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import com.xxh.jetpacksample.lifecycle.example.persistence.model.Comment
import java.util.Date

@Entity(
    tableName = "comments",
    foreignKeys = [ForeignKey(
        entity = ProductEntity::class,
        parentColumns = ["id"],
        childColumns = ["productId"],
        onDelete = CASCADE
    )],
    indices = [Index(value =["productId"] )]
)

/*@Entity(tableName = "comments",
    foreignKeys = {
        @ForeignKey(entity = ProductEntity.class,
                parentColumns = "id",
            childColumns = "productId",
            onDelete = ForeignKey.CASCADE)},
    indices = {@Index(value = "productId")
    })*/
class CommentEntity : Comment {
    @PrimaryKey(autoGenerate = true)
    override var id = 0
    override var productId = 0
    override var text: String? = null
    override var postedAt: Date? = null

    constructor()

    @Ignore
    constructor(id: Int, productId: Int, text: String?, postedAt: Date?) {
        this.id = id
        this.productId = productId
        this.text = text
        this.postedAt = postedAt
    }
}