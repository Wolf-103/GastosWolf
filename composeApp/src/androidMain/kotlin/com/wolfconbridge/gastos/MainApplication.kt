package com.wolfconbridge.gastos

import android.app.Application
import di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * @author Cristian Manuel Orozco - Orozcocristian860@gmail.com
 * @created 23/08/2024 20:21
 */
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@MainApplication)
            androidLogger()
            modules(appModule())
        }
    }
}