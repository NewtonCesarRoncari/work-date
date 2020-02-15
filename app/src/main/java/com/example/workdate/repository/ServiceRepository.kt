package com.example.workdate.repository

import androidx.lifecycle.LiveData
import com.example.workdate.database.dao.ServiceDAO
import com.example.workdate.model.Service
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ServiceRepository(private val dao: ServiceDAO) {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    fun insert(service: Service) {
        scope.launch {
            dao.insert(service)
        }
    }

    fun update(service: Service) {
        scope.launch {
            dao.update(service)
        }
    }

    fun remove(service: Service) = dao.remove(service)

    fun listAll(): LiveData<MutableList<Service>> = dao.listAll()
}