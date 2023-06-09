package com.coldwised.swipepix.domain.use_case

import com.coldwised.swipepix.data.remote.dto.CategoryDto
import com.coldwised.swipepix.domain.repository.ShopRepository
import com.coldwised.swipepix.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCatalogCategoriesUseCase @Inject constructor(
    private val repository: ShopRepository
) {
    operator fun invoke(parentId: String? = null): Flow<Resource<List<CategoryDto>>> {
        return if(parentId == null)
            repository.getCatalogCategories() else
                repository.getChildCategories(parentId)
    }
}