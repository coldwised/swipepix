package com.coldwised.swipepix.data.mappers

import com.coldwised.swipepix.data.remote.dto.OfferDto
import com.coldwised.swipepix.domain.model.OfferModel

fun OfferDto.toOfferModel(): OfferModel {
    return OfferModel(
        price = this.price,
        name = this.name,
        urlImageList = if(images.isEmpty()) listOf("") else images.map { it.src }
    )
}