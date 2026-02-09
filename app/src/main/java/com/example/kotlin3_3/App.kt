package com.example.kotlin3_3

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.kotlin3_3.data.preference.PreferencesHelper
import com.example.kotlin3_3.di.appModule
import com.example.kotlin3_3.di.repositoryModule
import com.example.kotlin3_3.di.viewModelModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, repositoryModule, viewModelModule))
        }
        applyTheme()
    }

    private fun applyTheme() {
        val preferencesHelper: PreferencesHelper by inject()
        if (preferencesHelper.isDarkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}
