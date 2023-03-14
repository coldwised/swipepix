package com.coldwised.swipepix.data.remote

import com.coldwised.swipepix.data.remote.dto.ImageDto
import com.coldwised.swipepix.domain.model.ImageModel
import io.ktor.client.*
import io.ktor.client.request.*

class ImagesServiceImpl(
    private val client: HttpClient
): ImagesService {
    override suspend fun getAllImages(): List<ImageModel> {
        return try {
            client.get<List<ImageDto>>(ImagesService.Endpoints.GetAllImages.url)
        } catch (e: Exception) {

        }
    }
}