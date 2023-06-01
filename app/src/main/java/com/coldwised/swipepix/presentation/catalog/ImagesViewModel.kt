package com.coldwised.swipepix.presentation.catalog

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldwised.swipepix.domain.use_case.*
import com.coldwised.swipepix.presentation.catalog.full_screen.event.ImageScreenEvent
import com.coldwised.swipepix.presentation.catalog.full_screen.type.AnimationType
import com.coldwised.swipepix.presentation.catalog.images_list.event.GalleryScreenEvent
import com.coldwised.swipepix.presentation.catalog.state.GalleryScreenState
import com.coldwised.swipepix.util.Extension.convertPixelsToDp
import com.coldwised.swipepix.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val getProductsByCategoryUseCase: GetProductsByCategoryUseCase,
    private val getAppConfigurationStreamUseCase: GetAppConfigurationStreamUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase,
    private val removeProductFromCartUseCase: RemoveProductFromCartUseCase,
    private val addProductToFavorites: AddProductToFavorites,
    private val removeProductFromFavorites: RemoveProductFromFavorites,
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
            is ImageScreenEvent.OnAddToCart -> {
                onCartClick(event.productId)
            }
            is ImageScreenEvent.OnRemoveFromCart -> {
                removeProductFromCart(event.productId)
            }
            is ImageScreenEvent.OnToggleFavorite -> {
                toggleFavorite(event.productId)
            }
        }
    }

    private fun toggleFavorite(id: String) {
        val viewModelScope = viewModelScope
        viewModelScope.launch {
            _state.update {
                it.copy(
                    goodsList = it.goodsList?.map { product ->
                        if(product.id == id) {
                            val inFavorites = product.favorite
                            if(inFavorites) {
                                removeFromFavorites(id)
                            } else {
                                addToFavorites(id)
                            }
                            product.copy(
                                favorite = !inFavorites
                            )
                        } else {
                            product
                        }
                    }.orEmpty()
                )
            }
        }
    }

    private fun addToFavorites(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            addProductToFavorites(id)
        }
    }

    private fun removeFromFavorites(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            removeProductFromFavorites(id)
        }
    }

    private fun removeProductFromCart(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            removeProductFromCartUseCase(id)
        }
    }

    private fun addProductToCart(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            addProductToCartUseCase(id)
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
            val state = _state
            state.update {
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
            delay(500)
            state.update {
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
            val product = it.goodsList?.get(index) ?: return
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
            is GalleryScreenEvent.OnCartClick -> {
                onCartClick(event.productId)
            }
        }
    }

    private fun onCartClick(id: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    goodsList = it.goodsList?.map { product ->
                        if(product.id == id) {
                            val inCart = product.inCart
                            if(inCart) {
                                removeProductFromCart(id)
                            } else {
                                addProductToCart(id)
                            }
                            product.copy(
                                inCart = !inCart
                            )
                        } else {
                            product
                        }
                    }
                )
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
            val product = it.goodsList?.get(index) ?: return
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
            delay(500)
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

    fun onStart(categoryId: String) {
        loadProducts(categoryId)
    }

    fun onStart(productIdList: List<String>) {
        loadProductsById(productIdList)
    }

    private fun loadProductsById(productIdList: List<String>) {
        viewModelScope.launch {

        }
    }

    private fun loadProducts(categoryId: String) {
        viewModelScope.launch {
            val state = _state
            state.update {
                it.copy(
                    isLoading = true,
                    error = null,
                )
            }
            getProductsByCategoryUseCase(categoryId).collect { result ->
                when(result) {
                    is Resource.Success -> {
                        state.update {
                            it.copy(
                                goodsList = result.data,
                            )
                        }
                    }
                    is Resource.Error -> {
                        state.update {
                            it.copy(
                                error = result.message,
                            )
                        }
                    }
                }
                state.update {
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
    }
}