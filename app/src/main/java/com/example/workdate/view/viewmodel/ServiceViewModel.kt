package com.example.workdate.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.workdate.model.Service
import com.example.workdate.repository.ServiceRepository

class ServiceViewModel(private val repository: ServiceRepository) : ViewModel() {

    fun insert(service: Service) = repository.insert(service)

    fun update(service: Service) = repository.update(service)

    fun remove(service: Service) = repository.remove(service)

    fun listAll(): LiveData<MutableList<Service>> = repository.listAll()
}