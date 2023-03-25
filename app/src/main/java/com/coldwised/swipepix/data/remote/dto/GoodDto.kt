package com.coldwised.swipepix.data.remote.dto

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "xml", strict = false)
data class GoodDto(
    @field:Element(name = "shop")
    @param:Element(name = "shop")
    var shop: ShopDto? = null,
)