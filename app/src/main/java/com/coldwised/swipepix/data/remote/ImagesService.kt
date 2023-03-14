package com.coldwised.swipepix.data.remote

import com.coldwised.swipepix.domain.model.ImageModel

interface ImagesService {

    suspend fun getAllImages(): List<ImageModel>

    companion object {
        const val BASE_URL = "http:/192.168.1.160:8080"
    }

    sealed class Endpoints(val url: String) {
        object GetAllImages: Endpoints("$BASE_URL/images")
    }
}