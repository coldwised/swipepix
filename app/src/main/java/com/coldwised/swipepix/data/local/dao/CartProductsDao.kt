package com.coldwised.swipepix.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coldwised.swipepix.data.local.entity.ProductEntity

@Dao
interface CartProductsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductEntity)

    @Query("DELETE FROM $CART_PRODUCTS_TABLE WHERE productId =:productId")
    suspend fun deleteProduct(productId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM $CART_PRODUCTS_TABLE WHERE productId =:productId)")
    suspend fun hasItem(productId: String): Boolean

    @Query("UPDATE $CART_PRODUCTS_TABLE SET amount = amount + :amount WHERE productId =:id")
    suspend fun increaseProductAmount(id: String, amount: Int)

    @Query("UPDATE $CART_PRODUCTS_TABLE SET amount = :amount WHERE productId =:id")
    suspend fun updateProductAmount(id: String, amount: Int)

    @Query("SELECT * FROM $CART_PRODUCTS_TABLE ORDER BY productId DESC")
    suspend fun getAllProducts(): List<ProductEntity>

    @Query("SELECT * FROM $CART_PRODUCTS_TABLE WHERE productId =:productId")
    suspend fun getProduct(productId: String): ProductEntity?

    companion object {
        const val CART_PRODUCTS_TABLE = "cart_products_table"
    }

}