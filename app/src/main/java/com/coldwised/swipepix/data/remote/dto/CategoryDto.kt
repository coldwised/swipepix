package com.coldwised.swipepix.data.remote.dto

data class CategoryDto(
    val id: String,
    val categoryImage: String,
    val name: String,
    val childCategories: List<Int>,
)