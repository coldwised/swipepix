package com.coldwised.swipepix.data.remote.dto

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Path
import org.simpleframework.xml.Root

@Root(name = "offer", strict = false)
data class OfferDto(
    @field:Attribute(name = "id")
    var id: Int? = null,

    @field:Element(name = "name", required = false)
    var name: String = "",

    @field:Path("images")
    @field:ElementList(name = "image", inline = true, required = false)
    var images: List<ImageDto> = mutableListOf(),
    @field:Element(name = "price", required = true)
    var price: Float = 0f,
)