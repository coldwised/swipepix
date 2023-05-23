package com.coldwised.swipepix.domain.use_case

import com.coldwised.swipepix.data.local.dao.FavoriteProductsDao
import com.coldwised.swipepix.data.local.entity.FavoriteEntity
import javax.inject.Inject

class AddProductToFavorites @Inject constructor(
    private val favoritesDao: FavoriteProductsDao,
) {
    suspend operator fun invoke(id: String) {
        favoritesDao.insertProduct(FavoriteEntity((id)))
    }
}