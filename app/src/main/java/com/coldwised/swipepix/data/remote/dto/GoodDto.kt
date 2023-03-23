package com.coldwised.swipepix.data.remote.dto

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Path
import org.simpleframework.xml.Root

@Root(name = "xml", strict = false)
data class GoodDto @JvmOverloads constructor(
    @field:Element(name = "shop")
    @param:Element(name = "shop")
    var shop: ShopDto? = null,

//    @field:ElementList(name = "offer", inline = true, required = true)
//    @param:ElementList(name = "offer", inline = true, required = true)
//    @field:Path("catalog")
//    @param:Path("catalog")
//    var offers: List<OfferDto> = emptyList(),
)