package com.example.kotlin3_3

import android.app.Application
import com.example.kotlin3_3.di.appModule
import com.example.kotlin3_3.di.repositoryModule
import com.example.kotlin3_3.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, repositoryModule, viewModelModule))
        }
    }
}