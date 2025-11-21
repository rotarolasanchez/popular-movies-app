package com.example.popular_movies_apps.di

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MoviesApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

        FirebaseAnalytics.getInstance(this).apply {
            setAnalyticsCollectionEnabled(true)
        }
    }
}