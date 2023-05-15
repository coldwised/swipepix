package com.coldwised.swipepix.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.coldwised.swipepix.data.local.dao.CartProductsDao.Companion.CART_PRODUCTS_TABLE

@Entity(tableName = CART_PRODUCTS_TABLE)
data class ProductEntity(
    @PrimaryKey
    val productId: String,
    val amount: Int
)