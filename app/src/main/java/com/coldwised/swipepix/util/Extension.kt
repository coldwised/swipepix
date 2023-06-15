package com.coldwised.swipepix.util

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.os.PowerManager
import android.util.DisplayMetrics
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.sp
import com.coldwised.swipepix.domain.type.ThemeStyleType


/**
 * Check if this device is compatible with Dynamic Colors for Material 3.
 *
 * @return true when this device is API 31 (Android 12) or up, false otherwise.
 */

object Extension {
    private lateinit var powerManager: PowerManager

    fun init(
        powerManager: PowerManager,
    ) {
        this.powerManager = powerManager
    }

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
    fun isCompatibleWithDynamicColors(): Boolean =
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.P)
    fun isCompatibleWithApi28(): Boolean =
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.P

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.M)
    fun isCompatibleWithApi23(): Boolean =
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.O)
    fun isCompatibleWithApi26(): Boolean =
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.N

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.Q)
    fun isCompatibleWithApi29(): Boolean =
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.TIRAMISU)
    fun isCompatibleWithApi33(): Boolean =
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU

    /**
     * Map a ThemeStyleType into a [Boolean].
     *
     * @param themeStyle the [ThemeStyleType].
     *
     * @return the corresponding boolean value of this ThemeStyleType.
     */
    @Composable
    fun shouldUseDarkTheme(
        themeStyle: ThemeStyleType,
    ): Boolean {
        val powerSavingMode = if(!isCompatibleWithApi29()) {
            isPowerSavingMode()
        } else {
            isSystemInDarkTheme()
        }
        return when (themeStyle) {
            ThemeStyleType.FollowAndroidSystem -> isSystemInDarkTheme()
            ThemeStyleType.LightMode -> false
            ThemeStyleType.DarkMode -> true
            ThemeStyleType.FollowPowerSavingMode -> powerSavingMode
        }
    }

    fun trySystemAction(action: () -> Unit): Boolean {
        return try {
            action()
            true
        } catch (throwable: Throwable) {
            try {
                action()
                true
            } catch (throwable: Throwable) {
                false
                // else do nothing
            }
        }
    }

    fun convertPixelsToDp(
        pixels: Float,
        context: Context?
    ): Float {
        return if(context != null) {
            val resources = context.resources
            val metrics = resources.displayMetrics
            (pixels / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT))
        } else {
            val metrics = Resources.getSystem().displayMetrics
            (pixels / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT))
        }
    }

    fun isPowerSavingMode(): Boolean {
        return try {
            powerManager.isPowerSaveMode
        } catch (e: Exception) {
            false
        }
    }

    val Int.nonScaledSp
        @Composable
        get() = (this / LocalDensity.current.fontScale).sp
}