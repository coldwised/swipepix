package com.coldwised.swipepix.presentation.gallery_screen.state

import androidx.compose.foundation.lazy.grid.LazyGridState
import com.coldwised.swipepix.data.remote.dto.GoodDto
import com.coldwised.swipepix.data.remote.dto.OfferDto
import com.coldwised.swipepix.presentation.gallery_screen.full_screen.state.PagerScreenState
import com.coldwised.swipepix.util.UiText

data class GalleryScreenState(
    val isLoading: Boolean = false,
    val goodsList: List<OfferDto> = emptyList(),
    val error: UiText? = null,
    val lazyGridState: LazyGridState = LazyGridState(firstVisibleItemIndex = 0, firstVisibleItemScrollOffset = 0),
    val itemOffsetToScroll: Int = 0,
    val pagerScreenState: PagerScreenState = PagerScreenState(),
)