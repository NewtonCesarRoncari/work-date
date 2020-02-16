package com.br.workdate.di

import androidx.room.Room
import com.br.workdate.database.ConnectionDatabase
import com.br.workdate.database.dao.ClientDAO
import com.br.workdate.database.dao.ServiceDAO
import com.br.workdate.repository.ClientRepository
import com.br.workdate.repository.ServiceRepository
import com.br.workdate.view.viewmodel.ClientViewModel
import com.br.workdate.view.viewmodel.ServiceViewModel
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