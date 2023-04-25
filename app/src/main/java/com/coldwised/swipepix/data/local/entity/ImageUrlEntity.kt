package com.coldwised.swipepix.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.coldwised.swipepix.Constants

@Entity(tableName = Constants.CART_PRODUCTS_TABLE)
data class ImageUrlEntity(
    var imageUrl: String,
    var location: String? = null,
    @PrimaryKey
    var id: Int? = null
)
