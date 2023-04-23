package com.coldwised.swipepix.presentation.gallery

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldwised.swipepix.presentation.gallery.full_screen.event.ImageScreenEvent
import com.coldwised.swipepix.presentation.gallery.full_screen.type.AnimationType
import com.coldwised.swipepix.presentation.gallery.images_list.event.GalleryScreenEvent
import com.coldwised.swipepix.presentation.gallery.state.GalleryScreenState
import com.coldwised.swipepix.util.Extension.convertPixelsToDp
import com.coldwised.swipepix.util.Resource
import com.coldwised.swipepix.domain.use_case.GetAppConfigurationStreamUseCase
import com.coldwised.swipepix.domain.use_case.GetImagesUrlListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val getImagesUrlListUseCase: GetImagesUrlListUseCase,
    private val getAppConfigurationStreamUseCase: GetAppConfigurationStreamUseCase,
): ViewModel() {

    private val _state = MutableStateFlow(GalleryScreenState())
    val state = _state.asStateFlow()

    init {
        init()
    }

    fun onImageScreenEvent(event: ImageScreenEvent) {
        when(event) {
            is ImageScreenEvent.OnAnimate -> {
                onAnimate(event.value)
            }
            is ImageScreenEvent.OnVisibleChanged -> {
                onImageVisibilityChange(event.value)
            }
            is ImageScreenEvent.OnPagerIndexChanged -> {
                onPagerIndexChanged(event.value)
            }
            is ImageScreenEvent.OnPagerCurrentImageChange -> {
                changeImageSize(event.painterIntrinsicSize)
            }
            is ImageScreenEvent.OnBarsVisibilityChange -> {
                onBarsVisibilityChange()
            }
            is ImageScreenEvent.OnBackToGallery -> {
                onBackClicked()
            }
            is ImageScreenEvent.OnCurrentScaleChange -> {
                changeCurrentScale(event.scale)
            }
        }
    }

    private fun changeCurrentScale(scale: Float) {
        _state.update {
            it.copy(
                pagerScreenState = it.pagerScreenState.copy(
                    currentScale = scale
                )
            )
        }
    }

    private fun onImageVisibilityChange(isVisible: Boolean) {
        _state.update {
            it.copy(
                pagerScreenState = it.pagerScreenState.copy(
                    isVisible = isVisible
                )
            )
        }
    }

    private fun onAnimate(animationType: AnimationType) {
        viewModelScope.launch {
            val _state = _state
            _state.update {
                val pagerScreenState = it.pagerScreenState
                it.copy(
                    pagerScreenState = pagerScreenState.copy(
                        animationState = pagerScreenState.animationState.copy(
                            isAnimationInProgress = true,
                            animationType = animationType
                        )
                    )
                )
            }
            delay(400)
            _state.update {
                val pagerScreenState = it.pagerScreenState
                it.copy(
                    pagerScreenState = pagerScreenState.copy(
                        animationState = pagerScreenState.animationState.copy(
                            isAnimationInProgress = false,
                        )
                    )
                )
            }
        }
    }

    private fun onPagerIndexChanged(index: Int) {
        _state.update {
            val product = it.goodsList[index]
            it.copy(
                pagerScreenState = it.pagerScreenState.copy(
                    pagerIndex = index,
                    topBarText = product.name
                )
            )
        }
    }

    private fun changeImageSize(size: Size) {
        _state.update {
            val imageState = it.pagerScreenState
            if(imageState.painterIntrinsicSize == size)
                return
            it.copy(
                pagerScreenState = imageState.copy(
                    painterIntrinsicSize = size
                )
            )
        }
    }

    private fun onBarsVisibilityChange() {
        _state.update {
            val pagerScreenState = it.pagerScreenState
            it.copy(
                pagerScreenState = pagerScreenState.copy(
                    topBarVisible = !pagerScreenState.topBarVisible,
                )
            )
        }
        onNavigationBarVisibilityChange()
    }

    private fun onNavigationBarVisibilityChange() {
        _state.update {
            val pagerScreenState = it.pagerScreenState
            it.copy(
                pagerScreenState = pagerScreenState
                    .copy(
                        systemNavigationBarVisible = pagerScreenState.topBarVisible
                    )
            )
        }
    }

    private suspend fun onScrollToImage(imageIndex: Int, isScrollToEnd: Boolean) {
        val stateValue = _state.value
        stateValue.lazyGridState.scrollToItem(
            index = imageIndex,
            scrollOffset =
            if(isScrollToEnd)
                -stateValue.itemOffsetToScroll else {
                0
            }
        )
    }

    private fun changeCurrentGridItemOffset(intOffset: IntOffset) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    pagerScreenState = it.pagerScreenState.copy(
                        imageOffset = intOffset
                    )
                )
            }
        }
    }

    fun onGalleryScreenEvent(event: GalleryScreenEvent) {
        when(event) {
            is GalleryScreenEvent.OnSaveGridItemOffsetToScroll -> {
                onItemOffsetToScrollChange(event.yOffset)
            }
            is GalleryScreenEvent.OnSavePainterIntrinsicSize -> {
                changeImageSize(event.size)
            }
            is GalleryScreenEvent.OnSaveCurrentGridItemOffset -> {
                changeCurrentGridItemOffset(event.intOffset)
            }
            is GalleryScreenEvent.OnImageClick -> {
                onImageClick(event.index)
            }
        }
    }

    private fun onItemOffsetToScrollChange(yOffset: Int) {
        _state.update {
            it.copy(
                itemOffsetToScroll = yOffset
            )
        }
    }

    private fun onImageClick(index: Int) {
        _state.update {
            val gridItemSize = it.lazyGridState.layoutInfo.visibleItemsInfo.first().size.toSize()
            val product = it.goodsList[index]
            it.copy(
                pagerScreenState = it.pagerScreenState.copy(
                    pagerIndex = index,
                    isVisible = true,
                    topBarText = product.name,
                    gridItemSize = Size(convertPixelsToDp(gridItemSize.width, null), convertPixelsToDp(gridItemSize.height, null)),
                ),
            )
        }
    }

    private fun onBackClicked() {
        val stateValue = state.value
        val imageStateValue = stateValue.pagerScreenState
        val layoutInfo = stateValue.lazyGridState.layoutInfo
        val visibleItemsInfo = layoutInfo.visibleItemsInfo
        val imageIndex = imageStateValue.pagerIndex
        val gridItem = visibleItemsInfo.find { it.index == imageIndex }
        val isScrollToEnd: Boolean?
        var indexToScroll = imageIndex
        val gridItemOffset = gridItem?.offset
        val gridItemOffsetY = gridItemOffset?.y
        if (gridItem == null) {
            isScrollToEnd = imageIndex > visibleItemsInfo.last().index
        } else if (gridItemOffsetY!! < 0) {
            isScrollToEnd = false
            indexToScroll = gridItem.index
        } else if (gridItemOffsetY + gridItem.size.height > layoutInfo.viewportSize.height) {
            isScrollToEnd = true
            indexToScroll = gridItem.index
        } else {
            isScrollToEnd = null
        }

        viewModelScope.launch {
            if(isScrollToEnd == null) {
                changeCurrentGridItemOffset(gridItemOffset!!)
            } else {
                viewModelScope.launch(Dispatchers.Main) {
                    onScrollToImage(indexToScroll, isScrollToEnd)
                }.join()
                val newGridItem = state.value.lazyGridState.layoutInfo.visibleItemsInfo.find { it.index == imageIndex } ?: visibleItemsInfo.last()
                changeCurrentGridItemOffset(newGridItem.offset)
            }
            val _state = _state
            _state.update {
                val pagerScreenState = it.pagerScreenState
                it.copy(
                    pagerScreenState = pagerScreenState.copy(
                        topBarVisible = false,
                        animationState = pagerScreenState.animationState.copy(
                            isAnimationInProgress = true,
                            animationType = AnimationType.HIDE_ANIMATION
                        )
                    )
                )
            }
            delay(350)
            _state.update {
                val pagerScreenState = it.pagerScreenState
                it.copy(
                    pagerScreenState = pagerScreenState.copy(
                        systemNavigationBarVisible = true,
                        currentScale = 1f,
                        topBarText = "",
                        isVisible = false,
                        animationState = pagerScreenState.animationState.copy(
                            isAnimationInProgress = true
                        )
                    )
                )
            }
        }
    }

    fun onRefresh() {
        loadImageUrlList()
    }

    private fun loadImageUrlList() {
        viewModelScope.launch {
            val _state = _state
            _state.update {
                it.copy(
                    isLoading = true,
                    error = null,
                )
            }
            getImagesUrlListUseCase().collect { result ->
                when(result) {
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                goodsList = result.data.toImmutableList(),
                            )
                        }
                    }
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                error = result.message,
                            )
                        }
                    }
                }
                _state.update {
                    it.copy(
                        isLoading = false,
                    )
                }
            }
        }
    }

    private fun init() {
        viewModelScope.launch {
            getAppConfigurationStreamUseCase().collectLatest { appConfiguration ->
                _state.update {
                    it.copy(
                        pagerScreenState = it.pagerScreenState.copy(
                            currentTheme = appConfiguration.themeStyle,
                        )
                    )
                }
            }
        }
        loadImageUrlList()
    }
}