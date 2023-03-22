package com.coldwised.swipepix.data.remote.dto

import org.simpleframework.xml.Root
import retrofit2.http.Field

@Root(name = "offers", strict = false)
data class GoodDto(
    @Field("name")
    val name: String,
    @Field("price")
    val price: Float,
    @Field("country")
    val country: String,
)