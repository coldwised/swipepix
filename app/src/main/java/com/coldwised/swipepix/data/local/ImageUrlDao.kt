package com.coldwised.swipepix.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coldwised.swipepix.Constants.CART_PRODUCTS_TABLE
import com.coldwised.swipepix.data.local.entity.ImageUrlEntity

@Dao
interface ImageUrlDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImageUrl(imageUrl: ImageUrlEntity)

    @Query("DELETE FROM $CART_PRODUCTS_TABLE WHERE imageUrl =:imageUrl")
    suspend fun deleteImageUrl(imageUrl: String)

    @Query("SELECT * FROM $CART_PRODUCTS_TABLE ORDER BY id DESC")
    suspend fun getImageUrlList(): List<ImageUrlEntity>

    @Query("SELECT EXISTS(SELECT * FROM $CART_PRODUCTS_TABLE WHERE imageUrl =:imageUrl)")
    suspend fun hasItem(imageUrl: String): Boolean

}