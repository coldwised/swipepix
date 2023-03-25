package com.coldwised.swipepix.data.remote

import com.coldwised.swipepix.data.remote.dto.ImageModelDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class ImagesServiceImpl(
    private val client: HttpClient
): ImagesService {
    override suspend fun getAllImages(): List<ImageModelDto> {
        return client.get{ url(ImagesService.Endpoints.GetAllImages.url) }.body()
    }
}