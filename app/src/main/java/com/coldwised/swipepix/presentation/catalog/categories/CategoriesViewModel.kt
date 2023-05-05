package com.coldwised.swipepix.presentation.catalog.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldwised.swipepix.domain.use_case.GetCatalogCategoriesUseCase
import com.coldwised.swipepix.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getCatalogCategoriesUseCase: GetCatalogCategoriesUseCase,
): ViewModel() {
    private val _state = MutableStateFlow(CategoriesScreenState())
    val state = _state.asStateFlow()

    init {
        loadCategories(null)
    }

    fun loadCategories(parentId: String?) {
        viewModelScope.launch {
            getCatalogCategoriesUseCase(parentId).collect { result ->
                when(result) {
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                categories = result.data,
                                isLoading = false,
                            )
                        }
                    }
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = result.message
                            )
                        }
                    }
                }
            }
        }
    }
}