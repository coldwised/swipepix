package com.coldwised.swipepix.data.remote.dto

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Path
import org.simpleframework.xml.Root

@Root(name = "xml", strict = false)
data class ShopDto @JvmOverloads constructor(
    @field:Element(name = "name")
    @param:Element(name = "name")
    var name: String = "",

    @field:ElementList(name = "offer")
    @param:ElementList(name = "offer")
    @field:Path("offers")
    @param:Path("offers")
    var offers: List<OfferDto> = emptyList(),
)