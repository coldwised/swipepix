package com.coldwised.swipepix.domain.use_case

import com.coldwised.swipepix.data.local.ImageUrlDao
import com.coldwised.swipepix.data.local.entity.ImageUrlEntity
import com.coldwised.swipepix.domain.type.LocationParams
import javax.inject.Inject

class AddImageUrlToRoomDbUseCase @Inject constructor(
    private val imageUrlDao: ImageUrlDao
) {
    suspend operator fun invoke(imageUrl: String, locationParams: LocationParams? = null) {
        val imageUrlDao = imageUrlDao
        if(imageUrlDao.hasItem(imageUrl)) {
            imageUrlDao.deleteImageUrl(imageUrl)
        }
        val location = locationParams?.let {
            "#${locationParams.latitude},${locationParams.longitude}"
        }
        imageUrlDao.insertImageUrl(ImageUrlEntity(imageUrl = imageUrl, location = location))
    }
}