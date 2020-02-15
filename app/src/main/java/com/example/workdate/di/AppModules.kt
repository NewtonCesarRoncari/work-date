package com.example.workdate.di

import androidx.room.Room
import com.example.workdate.database.ConnectionDatabase
import com.example.workdate.database.dao.ClientDAO
import com.example.workdate.repository.ClientRepository
import com.example.workdate.view.viewmodel.ClientViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

private const val NAME_DATABASE = "workdate.db"

val databaseModule = module {
    single<ConnectionDatabase> {
        Room.databaseBuilder(
            get(),
            ConnectionDatabase::class.java,
            NAME_DATABASE
        ).build()
    }
}

val daoModule = module {
    single<ClientDAO> { get<ConnectionDatabase>().clientDao() }
}

val repositoryModule = module {
    single<ClientRepository> { ClientRepository(dao = get()) }
}

val viewModelModule = module {
    viewModel<ClientViewModel> { ClientViewModel(repository = get()) }
}