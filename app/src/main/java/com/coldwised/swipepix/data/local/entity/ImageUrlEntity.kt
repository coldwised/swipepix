package com.coldwised.swipepix.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.coldwised.swipepix.Constants.IMAGES_URL_TABLE_NAME

@Entity(tableName = IMAGES_URL_TABLE_NAME)
data class ImageUrlEntity(
    var imageUrl: String,
    var location: String? = null,
    @PrimaryKey
    var id: Int? = null
)
