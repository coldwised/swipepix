package com.coldwised.swipepix.data.repository

import com.coldwised.swipepix.R
import com.coldwised.swipepix.data.local.dao.CartProductsDao
import com.coldwised.swipepix.data.local.dao.FavoriteProductsDao
import com.coldwised.swipepix.data.remote.ShopServiceApi
import com.coldwised.swipepix.data.remote.dto.CategoryDto
import com.coldwised.swipepix.data.remote.dto.ProductDto
import com.coldwised.swipepix.domain.model.CartProduct
import com.coldwised.swipepix.domain.repository.ShopRepository
import com.coldwised.swipepix.util.Resource
import com.coldwised.swipepix.util.UiText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ShopRepositoryImpl @Inject constructor(
    private val shopApi: ShopServiceApi,
    private val cartDao: CartProductsDao,
    private val favoritesDao: FavoriteProductsDao,
): ShopRepository {
    override fun getCatalogCategories(): Flow<Resource<List<CategoryDto>>> {
        return flow {
            emit(
                safeApiCall {
                    shopApi.getCatalogCategories()
                }
            )
        }.flowOn(Dispatchers.IO)
    }

    override fun getChildCategories(parentId: String): Flow<Resource<List<CategoryDto>>> {
        return flow {
            emit(
                safeApiCall {
                    shopApi.getChildCategories(parentId)
                }
            )
        }.flowOn(Dispatchers.IO)
    }

    override fun getProductsByCategory(categoryId: String): Flow<Resource<List<ProductDto>>> {
        return flow {
            emit(
                safeApiCall {
                    val cartDao = cartDao
                    val favoritesDao = favoritesDao
                    shopApi.getProductsByCategory(categoryId).map {
                        val newImages = it.images.let { images ->
                            images.ifEmpty {
                                listOf("https://media.istockphoto.com/id/924949200/vector/404-error-page-or-file-not-found-icon.jpg?s=170667a&w=0&k=20&c=gsR5TEhp1tfg-qj1DAYdghj9NfM0ldfNEMJUfAzHGtU=")
                            }
                        }
                        val productId = it.id
                        val inCart = cartDao.hasItem(productId)
                        val favorite = favoritesDao.hasItem(productId)
                        it.copy(
                            inCart = inCart,
                            images = newImages,
                            favorite = favorite
                        )
                    }
                }
            )
        }.flowOn(Dispatchers.IO)
    }

    override fun getFavoritesProducts(): Flow<Resource<List<ProductDto>>> {
        return flow {
            emit(
                safeApiCall {
                    val favoritesDao = favoritesDao
                    val cartDao = cartDao
                    val shopApi = shopApi
                    favoritesDao.getAllProducts().map { entity ->
                        val productId = entity.productId
                        val product = shopApi.getProductById(productId)
                        val newImages = product.images.let { images ->
                            images.ifEmpty {
                                listOf("https://media.istockphoto.com/id/924949200/vector/404-error-page-or-file-not-found-icon.jpg?s=170667a&w=0&k=20&c=gsR5TEhp1tfg-qj1DAYdghj9NfM0ldfNEMJUfAzHGtU=")
                            }
                        }
                        val inCart = cartDao.hasItem(productId)
                        val favorite = favoritesDao.hasItem(productId)
                        product.copy(
                            inCart = inCart,
                            favorite = favorite,
                            images = newImages,
                        )
                    }
                }
            )
        }.flowOn(Dispatchers.IO)
    }

    override fun getCartProducts(): Flow<Resource<List<CartProduct>>> {
        return flow {
            emit(
                safeApiCall {
                    val shopApi = shopApi
                    cartDao.getAllProducts().map {
                        val product = shopApi.getProductById(it.productId)
                        CartProduct(
                            id = product.id,
                            price = product.price,
                            name = product.name,
                            amount = it.amount,
                            image = product.images.let { images ->
                                if(images.isEmpty()) {
                                    "https://media.istockphoto.com/id/924949200/vector/404-error-page-or-file-not-found-icon.jpg?s=170667a&w=0&k=20&c=gsR5TEhp1tfg-qj1DAYdghj9NfM0ldfNEMJUfAzHGtU="
                                } else {
                                    images[0]
                                }
                            }
                        )
                    }
                }
            )
        }.flowOn(Dispatchers.IO)
    }

    override fun getProductsByQuery(query: String): Flow<Resource<List<ProductDto>>> {
        return flow {
            emit(
                safeApiCall {
                    val cartDao = cartDao
                    val favoritesDao = favoritesDao
                    val products = shopApi.getProductByQuery(query)
                    products.map {
                        val newImages = it.images.let { images ->
                            images.ifEmpty {
                                listOf("https://media.istockphoto.com/id/924949200/vector/404-error-page-or-file-not-found-icon.jpg?s=170667a&w=0&k=20&c=gsR5TEhp1tfg-qj1DAYdghj9NfM0ldfNEMJUfAzHGtU=")
                            }
                        }
                        val productId = it.id
                        val inCart = cartDao.hasItem(productId)
                        val favorite = favoritesDao.hasItem(productId)
                        it.copy(
                            inCart = inCart,
                            images = newImages,
                            favorite = favorite
                        )
                    }
                }
            )
        }.flowOn(Dispatchers.IO)
    }

    private inline fun <T> safeApiCall(apiCall: () -> T): Resource<T> {
        return try {
            val data = apiCall()
            Resource.Success(data = data)
        } catch(throwable: Throwable) {
            Resource.Error(handleThrowableException(throwable))
        }
    }

    private fun handleThrowableException(throwable: Throwable): UiText {
        return when(throwable) {
            is HttpException -> {
                val localizedMessage = throwable.localizedMessage
                if(localizedMessage.isNullOrEmpty()) {
                    UiText.StringResource(R.string.unknown_exception)
                }
                else {
                    UiText.DynamicString(localizedMessage)
                }
            }
            is IOException -> {
                UiText.StringResource(R.string.io_exception)
            }
            else -> UiText.StringResource(R.string.unknown_exception)
        }
    }

}