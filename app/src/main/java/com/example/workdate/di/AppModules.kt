package com.example.workdate.di

import androidx.room.Room
import com.example.workdate.database.ConnectionDatabase
import com.example.workdate.database.dao.ClientDAO
import com.example.workdate.database.dao.ServiceDAO
import com.example.workdate.repository.ClientRepository
import com.example.workdate.repository.ServiceRepository
import com.example.workdate.view.viewmodel.ClientViewModel
import com.example.workdate.view.viewmodel.ServiceViewModel
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
    single<ServiceDAO> { get<ConnectionDatabase>().serviceDao() }
}

val repositoryModule = module {
    single<ClientRepository> { ClientRepository(dao = get()) }
    single<ServiceRepository> { ServiceRepository(dao = get()) }
}

val viewModelModule = module {
    viewModel<ClientViewModel> { ClientViewModel(repository = get()) }
    viewModel<ServiceViewModel> { ServiceViewModel(repository = get()) }
}