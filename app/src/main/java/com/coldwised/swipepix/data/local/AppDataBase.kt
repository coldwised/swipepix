package com.coldwised.swipepix.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.coldwised.swipepix.data.local.dao.CartProductsDao
import com.coldwised.swipepix.data.local.dao.FavoriteProductsDao
import com.coldwised.swipepix.data.local.entity.FavoriteEntity
import com.coldwised.swipepix.data.local.entity.ProductEntity

@Database(
    entities = [ProductEntity::class, FavoriteEntity::class],
    version = 1
)

abstract class AppDatabase: RoomDatabase() {

    abstract val cartProductsDao: CartProductsDao

    abstract val favoriteProductsDao: FavoriteProductsDao

    companion object {
        const val name = "app_dp"
    }
}