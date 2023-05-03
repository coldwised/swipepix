package com.coldwised.swipepix.domain.repository

import com.coldwised.swipepix.data.remote.dto.CategoryDto
import com.coldwised.swipepix.data.remote.dto.ProductDto
import com.coldwised.swipepix.util.Resource
import kotlinx.coroutines.flow.Flow

interface ShopRepository {
    fun getCatalogCategories(): Flow<Resource<List<CategoryDto>>>

    fun getChildCategories(parentId: String): Flow<Resource<List<CategoryDto>>>

    fun getProductsByCategory(categoryId: String): Flow<Resource<List<ProductDto>>>
}