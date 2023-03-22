package com.coldwised.swipepix.data.remote

import com.coldwised.swipepix.data.remote.dto.GoodDto
import retrofit2.http.GET

interface GoodsApi {

    @GET("files/prices/price_list.xml")
    suspend fun getAllGoods(): List<GoodDto>

    companion object {
        const val BASE_URL = "https://www.mctrade.ru/"
    }
}