package com.coldwised.swipepix.data.remote.dto

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root

@Root(name = "image", strict = false)
data class ImageDto(
    @field:Attribute(name = "src")
    var src: String = ""
)