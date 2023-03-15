package com.coldwised.swipepix.data.remote

import com.coldwised.swipepix.domain.model.ImageModel
import com.coldwised.swipepix.util.Resource
import kotlinx.coroutines.flow.Flow

interface GallerySocketService {

    suspend fun initSession(
        username: String,
    ): Resource<Unit>

    suspend fun sendImage(
        imageUrl: String,
    )

    fun observeImages(): Flow<ImageModel>

    suspend fun closeSession()

}