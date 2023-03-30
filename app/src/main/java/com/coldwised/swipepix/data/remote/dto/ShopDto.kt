package com.coldwised.swipepix.data.remote.dto

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Path
import org.simpleframework.xml.Root

@Root(name = "shop", strict = false)
data class ShopDto(
    @field:Element(name = "name")
    var name: String = "",

    @field:Path("offers")
    @field:ElementList(name = "offer", inline = true)
    var offers: List<OfferDto>? = null,

    @field:Path("categories")
    @field:ElementList(name = "category", inline = true)
    var categories: List<CategoryDto>? = null,
)