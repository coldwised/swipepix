package com.coldwised.swipepix.data.remote.dto

import com.coldwised.swipepix.domain.model.ImageModel
import java.text.DateFormat
import java.util.*

@kotlinx.serialization.Serializable
data class ImageModelDto(
    val url: String,
    val timeStamp: Long,
    val username: String,
    val id: String,
) {
    fun toImageModel(): ImageModel {
        val date = Date(timeStamp)
        val formattedDate = DateFormat
            .getDateInstance(DateFormat.DEFAULT)
            .format(date)
        return ImageModel(
            url = url,
            username = username,
            formattedTime = formattedDate,
            id = id
        )
    }
}