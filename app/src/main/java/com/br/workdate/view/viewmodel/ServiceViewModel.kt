package com.br.workdate.view.viewmodel

import androidx.lifecycle.ViewModel
import com.br.workdate.model.Service
import com.br.workdate.repository.ServiceRepository

class ServiceViewModel(private val repository: ServiceRepository) : ViewModel() {

    fun insert(service: Service) = repository.insert(service)

    fun update(service: Service) = repository.update(service)

    fun remove(service: Service, inFailureCase: () -> Unit, inSuccessCase: () -> Unit) =
        repository.remove(service, inFailureCase, inSuccessCase)

    fun listAll() = repository.listAll()

    fun returnDescriptionForId(id: String) = repository.returnDescriptionForId(id)

    fun returnForId(id: String) = repository.returnForId(id)
}