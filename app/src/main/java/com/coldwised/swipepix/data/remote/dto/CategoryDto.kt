package com.coldwised.swipepix.data.remote.dto

data class CategoryDto(
    val id: String,
    val image: String,
    val name: String,
    val childCategories: List<Int>,
)