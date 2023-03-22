package com.coldwised.swipepix

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldwised.swipepix.domain.type.ThemeStyleType
import com.coldwised.swipepix.domain.use_case.GetAppConfigurationStreamUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * Data class that represents the state of the view model.
 */
data class MainViewModelState(
    val isLoading: Boolean = true,
    val useDynamicColors: Boolean = true,
    val themeStyle: ThemeStyleType = ThemeStyleType.FollowAndroidSystem,
    val usePowerModeSaving: Boolean = false,
    val currentUrl: String? = null
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAppConfigurationStreamUseCase: GetAppConfigurationStreamUseCase,
) : ViewModel() {
    private val viewModelState = MutableStateFlow(value = MainViewModelState())
    val activityState = viewModelState.asStateFlow()

    init {
        watchAppConfigurationStream()
    }

    private fun watchAppConfigurationStream() {
        viewModelScope.launch {
            val viewModelState = viewModelState
            viewModelState.update { it.copy(isLoading = true) }
            getAppConfigurationStreamUseCase().collectLatest { appConfiguration ->
                viewModelState.update { state ->
                    state.copy(
                        isLoading = false,
                        useDynamicColors = appConfiguration.useDynamicColors,
                        usePowerModeSaving = appConfiguration.usePowerSavingMode,
                        themeStyle = appConfiguration.themeStyle
                    )
                }
            }
        }
    }
}