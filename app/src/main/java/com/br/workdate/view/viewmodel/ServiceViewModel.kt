package com.br.workdate.view.viewmodel

import androidx.lifecycle.ViewModel
import com.br.workdate.model.Service
import com.br.workdate.repository.ServiceRepository

class ServiceViewModel(private val repository: ServiceRepository) : ViewModel() {

    fun insert(
        service: Service,
        inFailureCase: () -> Unit,
        inSuccessCase: () -> Unit
    ) = repository.insert(service, inFailureCase, inSuccessCase)

    fun update(
        service: Service,
        inFailureCase: () -> Unit,
        inSuccessCase: () -> Unit
    ) = repository.update(service, inFailureCase, inSuccessCase)

    fun remove(
        service: Service,
        inFailureCase: () -> Unit,
        inSuccessCase: () -> Unit
    ) = repository.remove(service, inFailureCase, inSuccessCase)

    fun listAll() = repository.listAll()

    fun returnDescriptionForId(id: String) = repository.returnDescriptionForId(id)

    fun returnForId(id: String) = repository.returnForId(id)
}