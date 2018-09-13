package com.brunodiegom.lanchonete

import android.app.Application
import com.brunodiegom.lanchonete.component.AppModule.appModule
import org.koin.android.ext.android.startKoin

class InitApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule))
    }
}