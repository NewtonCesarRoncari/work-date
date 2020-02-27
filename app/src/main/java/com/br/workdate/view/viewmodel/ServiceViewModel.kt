package com.br.workdate.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.br.workdate.model.Service
import com.br.workdate.repository.ServiceRepository

class ServiceViewModel(private val repository: ServiceRepository) : ViewModel() {

    fun insert(service: Service) = repository.insert(service)

    fun update(service: Service) = repository.update(service)

    fun remove(service: Service) = repository.remove(service)

    fun listAll(): LiveData<MutableList<Service>> = repository.listAll()

    fun returnDescriptionForId(id: String): LiveData<String> = repository.returnDescriptionForId(id)

    fun returnForId(id: String): LiveData<Service> = repository.returnForId(id)
}