package com.example.imdumb

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.imdumb.data.local.LocalConfig
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class ImdumbApp : Application() {

    @Inject
    lateinit var localConfig: LocalConfig

    override fun onCreate() {
        super.onCreate()
        applySavedTheme()
    }

    private fun applySavedTheme() {
        val theme = localConfig.getConfig("app_theme") ?: "light"
        val mode = if (theme == "dark") {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }
        AppCompatDelegate.setDefaultNightMode(mode)
    }
}
