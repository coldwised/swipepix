package com.coldwised.swipepix.data.remote.dto

data class CategoryDto(
    val id: Int,
    val categoryImage: String,
    val name: String,
    val childCategories: List<Int>,
)