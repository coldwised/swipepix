package com.coldwised.swipepix.data.remote

import com.coldwised.swipepix.data.remote.dto.CategoryDto
import com.coldwised.swipepix.data.remote.dto.ProductDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ShopServiceApi {

    @GET("/categories")
    suspend fun getCatalogCategories(): List<CategoryDto>

    @GET("/child_categories")
    suspend fun getChildCategories(
        @Query("parentId") parentId: String
    ): List<CategoryDto>

    @GET("/get_products_by_category")
    suspend fun getProductsByCategory(
        @Query("categoryId") categoryId: String
    ): List<ProductDto>

    companion object {
        const val BASE_URL = "http:/192.168.1.160:8080"
    }
}