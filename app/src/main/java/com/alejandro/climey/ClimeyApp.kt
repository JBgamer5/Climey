package com.alejandro.climey

import android.app.Application
import com.alejandro.climey.di.dataModule
import com.alejandro.climey.di.domainModule
import com.alejandro.climey.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class ClimeyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@ClimeyApp)
            modules(dataModule + domainModule + presentationModule)
        }
    }
}