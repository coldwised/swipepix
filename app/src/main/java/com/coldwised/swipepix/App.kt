package com.coldwised.swipepix

import android.app.Application
import android.os.PowerManager
import com.coldwised.swipepix.util.Extension
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App: Application() {
    @Inject
    lateinit var powerManager: PowerManager
    override fun onCreate() {
        super.onCreate()
        Extension.init(
            powerManager = powerManager,
        )
    }
}