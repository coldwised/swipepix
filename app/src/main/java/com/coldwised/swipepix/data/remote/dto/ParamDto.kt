package com.coldwised.swipepix.data.remote.dto

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root

@Root(name = "image", strict = false)
data class ParamDto(
	@field:Attribute(name = "name")
	var name: String = "",
	@field:Attribute(name = "value")
	var value: String = ""
)