package com.coldwised.swipepix.domain.use_case

import com.coldwised.swipepix.domain.model.OfferModel
import com.coldwised.swipepix.domain.repository.GoodsRepository
import com.coldwised.swipepix.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetImagesUrlListUseCase @Inject constructor(
    private val repository: GoodsRepository,
) {
    operator fun invoke(): Flow<Resource<List<OfferModel>>> {
        return repository.getAllGoods()
    }
}