package com.coldwised.swipepix.domain.use_case

import com.coldwised.swipepix.data.local.dao.CartProductsDao
import com.coldwised.swipepix.data.local.entity.ProductEntity
import javax.inject.Inject

class AddProductToCartUseCase @Inject constructor(
    private val cartProductsDao: CartProductsDao
) {
    suspend operator fun invoke(id: String, amount: Int = 1) {
        val cartProductsDao = cartProductsDao
        if(cartProductsDao.hasItem(id)) {
            cartProductsDao.increaseProductAmount(id, amount)
        } else {
            cartProductsDao.insertProduct(ProductEntity(id, amount))
        }
    }
}