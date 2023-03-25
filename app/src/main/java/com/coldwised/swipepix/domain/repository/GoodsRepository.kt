package com.coldwised.swipepix.domain.repository

import com.coldwised.swipepix.domain.model.OfferModel
import com.coldwised.swipepix.util.Resource
import kotlinx.coroutines.flow.Flow

interface GoodsRepository {

    fun getAllGoods(): Flow<Resource<List<OfferModel>>>
}