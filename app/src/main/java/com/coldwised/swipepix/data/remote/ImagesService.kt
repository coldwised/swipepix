package com.coldwised.swipepix.data.remote

import com.coldwised.swipepix.data.remote.dto.ImageModelDto

interface ImagesService {

    suspend fun getAllImages(): List<ImageModelDto>

    companion object {
        const val BASE_URL = "http:/192.168.1.160:8080"
    }

    sealed class Endpoints(val url: String) {
        object GetAllImages: Endpoints("$BASE_URL/images")
    }
}