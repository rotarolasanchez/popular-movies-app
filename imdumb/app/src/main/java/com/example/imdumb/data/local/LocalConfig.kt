package com.example.imdumb.data.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalConfig @Inject constructor(
    @ApplicationContext context: Context
) {
    private val prefs = context.getSharedPreferences("app_config", Context.MODE_PRIVATE)

    fun saveConfig(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }

    fun getConfig(key: String): String? {
        return prefs.getString(key, null)
    }

    fun saveBoolean(key: String, value: Boolean) {
        prefs.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String, default: Boolean = false): Boolean {
        return prefs.getBoolean(key, default)
    }
}
