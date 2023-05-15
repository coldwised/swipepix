package com.coldwised.swipepix.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.coldwised.swipepix.data.local.dao.CartProductsDao
import com.coldwised.swipepix.data.local.entity.ProductEntity

@Database(
    entities = [ProductEntity::class],
    version = 3
)

abstract class AppDatabase: RoomDatabase() {

    abstract val cartProductsDao: CartProductsDao

    companion object {
        const val name = "app_dp"
    }
}