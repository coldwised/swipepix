package com.coldwised.swipepix.domain.use_case

import com.coldwised.swipepix.data.local.dao.FavoriteProductsDao
import javax.inject.Inject

class RemoveProductFromFavorites @Inject constructor(
    private val favoritesDao: FavoriteProductsDao,
) {

    suspend operator fun invoke(id: String) {
        favoritesDao.deleteProduct(id)
    }
}