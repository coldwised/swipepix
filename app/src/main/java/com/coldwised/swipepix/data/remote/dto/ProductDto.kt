package com.coldwised.swipepix.data.remote.dto

data class ProductDto(
    val id: String,
    val images: List<String>,
    val rating: Float?,
    val name: String,
    val description: String?,
    val inCart: Boolean = false,
    val favorite: Boolean = false,
    val price: Float,
    val country: String?,
    val categoryId: String,
    val params: List<Pair<String, String>>
)