package com.coldwised.swipepix.data.repository

import com.coldwised.swipepix.R
import com.coldwised.swipepix.data.remote.GoodsApi
import com.coldwised.swipepix.data.remote.dto.GoodDto
import com.coldwised.swipepix.data.remote.dto.OfferDto
import com.coldwised.swipepix.domain.repository.GoodsRepository
import com.coldwised.swipepix.util.Resource
import com.coldwised.swipepix.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GoodsRepositoryImpl @Inject constructor(
    private val goodsApi: GoodsApi,
): GoodsRepository {
    override fun getAllGoods(): Flow<Resource<List<OfferDto>>> {
        return flow {
            emit(
                safeApiCall {
                    val f = goodsApi.getAllGoods()
                    //val df = f.shop
                    //val g = df
                    val d = f.shop!!.name
                    val ff = d
                    listOf()
                }
            )
        }
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