package com.br.workdate

import android.app.Application
import com.br.workdate.di.daoModule
import com.br.workdate.di.databaseModule
import com.br.workdate.di.repositoryModule
import com.br.workdate.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
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
    }
}