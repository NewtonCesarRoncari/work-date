package com.br.workdate

import android.app.Application
import com.br.workdate.di.daoModule
import com.br.workdate.di.databaseModule
import com.br.workdate.di.repositoryModule
import com.br.workdate.di.viewModelModule
import com.br.workdate.extension.TypefaceUtil
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    databaseModule,
                    daoModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
        val typefaceUtil = TypefaceUtil()
        typefaceUtil.overrideFonts(this, "SERIF", "fonts/OpenSans-Semibold.ttf")
    }
}