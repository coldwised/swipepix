package com.coldwised.swipepix.data.remote.dto

data class ProductDto(
    val id: String,
    val images: List<String>,
    val rating: Float? = null,
    val name: String,
    val description: String,
    val price: Float,
    val country: String,
    val categoryId: Int,
    val params: List<Pair<String, String>>
)