package com.coldwised.swipepix.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coldwised.swipepix.Constants.IMAGES_URL_TABLE_NAME
import com.coldwised.swipepix.data.local.entity.ImageUrlEntity

@Dao
interface ImageUrlDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImageUrl(imageUrl: ImageUrlEntity)

    @Query("DELETE FROM $IMAGES_URL_TABLE_NAME WHERE imageUrl =:imageUrl")
    suspend fun deleteImageUrl(imageUrl: String)

    @Query("SELECT * FROM $IMAGES_URL_TABLE_NAME ORDER BY id DESC")
    suspend fun getImageUrlList(): List<ImageUrlEntity>

    @Query("SELECT EXISTS(SELECT * FROM $IMAGES_URL_TABLE_NAME WHERE imageUrl =:imageUrl)")
    suspend fun hasItem(imageUrl: String): Boolean

}