package com.coldwised.swipepix.domain.repository

import com.coldwised.swipepix.data.remote.dto.GoodDto
import com.coldwised.swipepix.util.Resource
import kotlinx.coroutines.flow.Flow

interface GoodsRepository {

    fun getAllGoods(): Flow<Resource<List<GoodDto>>>
}