package com.example.kotlin3_3.ui.preference

import android.app.Application

class App : Application() {
    companion object {
        var preferenceHelper: PreferencesHelper? = null
    }

    override fun onCreate() {
        super.onCreate()
        preferenceHelper = PreferencesHelper(applicationContext)
    }
}
