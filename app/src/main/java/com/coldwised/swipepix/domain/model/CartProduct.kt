package com.coldwised.swipepix.domain.model

data class CartProduct(
    val id: String,
    val image: String,
    val price: Float,
    val name: String,
    val amount: Int,
)