package com.coldwised.swipepix.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coldwised.swipepix.data.local.entity.FavoriteEntity

@Dao
interface FavoriteProductsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: FavoriteEntity)

    @Query("DELETE FROM $FAVORITE_PRODUCTS_TABLE WHERE productId =:productId")
    suspend fun deleteProduct(productId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM $FAVORITE_PRODUCTS_TABLE WHERE productId =:productId)")
    suspend fun hasItem(productId: String): Boolean

    @Query("SELECT * FROM $FAVORITE_PRODUCTS_TABLE ORDER BY productId DESC")
    suspend fun getAllProducts(): List<FavoriteEntity>

    @Query("SELECT * FROM $FAVORITE_PRODUCTS_TABLE WHERE productId =:productId")
    suspend fun getProduct(productId: String): FavoriteEntity?

    companion object {
        const val FAVORITE_PRODUCTS_TABLE = "favorite_products_table"
    }
}