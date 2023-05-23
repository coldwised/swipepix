package com.coldwised.swipepix.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.coldwised.swipepix.data.local.dao.FavoriteProductsDao.Companion.FAVORITE_PRODUCTS_TABLE

@Entity(tableName = FAVORITE_PRODUCTS_TABLE)
data class FavoriteEntity(
    @PrimaryKey
    val productId: String,
)