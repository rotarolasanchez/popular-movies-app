package com.example.imdumb.data.local

import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalConfig @Inject constructor(context: Context) {
    private val prefs = context.getSharedPreferences("app_config", Context.MODE_PRIVATE)

    fun saveConfig(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }

    fun getConfig(key: String): String? {
        return prefs.getString(key, null)
    }
}
