package com.coldwised.swipepix.domain.model

data class OfferModel(
    val urlImageList: List<String>,
    val price: Float,
    val name: String,
)