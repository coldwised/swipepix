package com.coldwised.swipepix.presentation.gallery_screen.state

import androidx.compose.foundation.lazy.grid.LazyGridState
import com.coldwised.swipepix.domain.model.OfferModel
import com.coldwised.swipepix.presentation.gallery_screen.full_screen.state.PagerScreenState
import com.coldwised.swipepix.util.UiText

data class GalleryScreenState(
    val isLoading: Boolean = false,
    val goodsList: List<OfferModel> = emptyList(),
    val error: UiText? = null,
    val lazyGridState: LazyGridState = LazyGridState(firstVisibleItemIndex = 0, firstVisibleItemScrollOffset = 0),
    val itemOffsetToScroll: Int = 0,
    val pagerScreenState: PagerScreenState = PagerScreenState(),
)