package com.coldwised.swipepix.data.remote.dto

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Path
import org.simpleframework.xml.Root

@Root(name = "shop", strict = false)
data class ShopDto(
    @field:Element(name = "name")
    //@param:Element(name = "name")
    var name: String = "",

//    @field:Element(name = "offers")
//    //@param:Element(name = "name")
//    var offersDto: OffersDto? = null,

    @field:Path("offers")
    @field:ElementList(name = "offer", inline = true)
//    @param:ElementList(name = "offer")
//    @field:Path("offers")
//    @param:Path("offers")
    var offers: List<OfferDto>? = null,
)