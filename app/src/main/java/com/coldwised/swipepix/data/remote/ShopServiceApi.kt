package com.coldwised.swipepix.data.remote

import com.coldwised.swipepix.data.remote.dto.CategoryDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ShopServiceApi {

    @GET("/categories")
    suspend fun getCatalogCategories(): List<CategoryDto>

    @GET("/child_categories")
    suspend fun getChildCategories(
        @Query("parentId") parentId: String
    ): List<CategoryDto>

    companion object {
        const val BASE_URL = "http:/192.168.1.160:8080"
        //const val BASE_URL = "http:/192.168.91.120:8080"
    }
}