package com.coldwised.swipepix.data.remote

import com.coldwised.swipepix.data.remote.dto.CategoryDto
import retrofit2.http.GET

interface ShopServiceApi {

    @GET("/categories")
    suspend fun getCatalogCategories(): List<CategoryDto>

    companion object {
        const val BASE_URL = "http:/192.168.1.160:8080"
    }
}