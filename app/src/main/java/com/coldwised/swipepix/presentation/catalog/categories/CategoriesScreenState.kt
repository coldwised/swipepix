package com.coldwised.swipepix.presentation.catalog.categories

import com.coldwised.swipepix.data.remote.dto.CategoryDto
import com.coldwised.swipepix.util.UiText

data class CategoriesScreenState(
    val categories: List<CategoryDto> = emptyList(),
    val isLoading: Boolean = true,
    val error: UiText? = null,
)