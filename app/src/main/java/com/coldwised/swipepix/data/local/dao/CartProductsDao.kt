package com.coldwised.swipepix.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coldwised.swipepix.data.local.entity.ProductEntity

@Dao
interface CartProductsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(productId: ProductEntity)

    @Query("DELETE FROM $CART_PRODUCTS_TABLE WHERE productId =:productId")
    suspend fun deleteProduct(productId: String)

    @Query("SELECT * FROM $CART_PRODUCTS_TABLE ORDER BY productId DESC")
    suspend fun getImageUrlList(): List<ProductEntity>

    companion object {
        const val CART_PRODUCTS_TABLE = "cart_products_table"
    }

}