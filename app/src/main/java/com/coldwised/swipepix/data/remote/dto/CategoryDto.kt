package com.coldwised.swipepix.data.remote.dto

import org.simpleframework.xml.Attribute

data class CategoryDto(
    @field:Attribute(name = "name")
    var name: String = "",
    @field:Attribute(name = "id")
    var id: Int = 0,
    @field:Attribute(name = "parentId")
    var parentId: Int = 0
)