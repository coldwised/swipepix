package com.coldwised.swipepix.domain.model

import com.coldwised.swipepix.data.remote.dto.ParamDto

data class OfferModel(
    val urlImageList: List<String>,
    val price: Float,
    val name: String,
    val params: List<ParamDto>
)