package com.coldwised.swipepix.presentation.catalog.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldwised.swipepix.domain.use_case.GetCatalogCategoriesUseCase
import com.coldwised.swipepix.domain.use_case.GetProductsByQueryUseCase
import com.coldwised.swipepix.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getCatalogCategoriesUseCase: GetCatalogCategoriesUseCase,
    private val getProductsByQueryUseCase: GetProductsByQueryUseCase,
): ViewModel() {
    private val _state = MutableStateFlow(CategoriesScreenState())
    val state = _state.asStateFlow()

    fun loadCategories(parentId: String?) {
        val state = _state
        state.update {
            it.copy(
                searchQuery = ""
            )
        }
        if(state.value.categories != null)
            return
        viewModelScope.launch {
            delay(200)
            if(state.value.categories == null) {
                state.update {
                    it.copy(
                        isLoading = true
                    )
                }
            }
        }
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

    fun onSearchShow() {
        _state.update {
            it.copy(
                foundProducts = emptyList(),
            )
        }
    }

    fun onSearchHide() {
        _state.update {
            it.copy(
                foundProducts = null,
                searchQuery = "",
                isLoading = false,
             )
        }
    }

    private var searchJob: Job? = null
    fun onSearchQueryChanged(query: String) {
        searchJob?.cancel()
        if(query.isEmpty()) {
            _state.update {
                it.copy(
                    isLoading = false,
                    searchQuery = query,
                    foundProducts = emptyList()
                )
            }
            return
        }
        searchJob = viewModelScope.launch {
            val state = _state
            state.update {
                it.copy(
                    isLoading = true,
                    searchQuery = query
                )
            }
            delay(500L)
            getProductsByQueryUseCase(query).collect { result ->
                when(result) {
                    is Resource.Success -> {
                        state.update {
                            it.copy(
                                foundProducts = result.data,
                                isLoading = false,
                            )
                        }
                    }
                    is Resource.Error -> {
                        state.update {
                            it.copy(
                                foundProducts = null,
                                error = result.message,
                                isLoading = false,
                            )
                        }
                    }
                }
            }
        }
    }
}