package com.coldwised.swipepix.data.remote.dto

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "offer", strict = false)
data class OfferDto @JvmOverloads constructor(
    @field:Element(name = "name")
    @param:Element(name = "name")
    var name: String = "null",
    @field:Element(name = "price")
    @param:Element(name = "price")
    var price: String = "null",
//    @field:Element(name = "country")
//    @param:Element(name = "country")
//    var country: String = "null",
)