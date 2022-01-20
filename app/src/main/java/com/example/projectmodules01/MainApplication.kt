package com.example.projectmodules01

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MainApplication : Application() {

    companion object {
        private const val DEBUG = true
    }

    override fun onCreate() {
        super.onCreate()

        if(DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

    }
}