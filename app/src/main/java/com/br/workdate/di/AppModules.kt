package com.br.workdate.di

import android.content.SharedPreferences
import androidx.room.Room
import com.br.workdate.database.ConnectionDatabase
import com.br.workdate.database.dao.ClientDAO
import com.br.workdate.database.dao.ReleaseDAO
import com.br.workdate.database.dao.ScheduleDAO
import com.br.workdate.database.dao.ServiceDAO
import com.br.workdate.repository.*
import com.br.workdate.view.viewmodel.*
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
    single<ReleaseDAO> { get<ConnectionDatabase>().releaseDao() }
    single<ScheduleDAO> { get<ConnectionDatabase>().scheduleDao() }
    single<SharedPreferences> {
        androidx.preference.PreferenceManager.getDefaultSharedPreferences(
            get()
        )
    }
}

val repositoryModule = module {
    single<ClientRepository> { ClientRepository(dao = get()) }
    single<ServiceRepository> { ServiceRepository(dao = get()) }
    single<ReleaseRepository> { ReleaseRepository(dao = get()) }
    single<ScheduleRepository> { ScheduleRepository(dao = get()) }
    single<FilterRepository> { FilterRepository(clientDAO = get(), serviceDAO = get()) }
    single<LoginRepository> { LoginRepository(preferences = get()) }
}

val viewModelModule = module {
    viewModel<ClientViewModel> { ClientViewModel(repository = get()) }
    viewModel<ServiceViewModel> { ServiceViewModel(repository = get()) }
    viewModel<ScheduleViewModel> { ScheduleViewModel(repository = get()) }
    viewModel<ReleaseViewModel> { ReleaseViewModel(repository = get()) }
    viewModel<StateAppComponentsViewModel> { StateAppComponentsViewModel() }
    viewModel<FilterViewModel> { FilterViewModel(repository = get()) }
    viewModel<LoginViewModel> { LoginViewModel(repository = get()) }
}