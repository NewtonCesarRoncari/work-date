package com.example.workdate

import android.app.Application
import com.example.workdate.di.daoModule
import com.example.workdate.di.databaseModule
import com.example.workdate.di.repositoryModule
import com.example.workdate.di.viewModelModule
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