package com.br.workdate.repository

import android.database.sqlite.SQLiteConstraintException
import com.br.workdate.database.dao.ServiceDAO
import com.br.workdate.model.Service
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

    fun remove(
        service: Service,
        inFailureCase: () -> Unit,
        inSuccessCase: () -> Unit
    ) {
        scope.launch {
            var failure = false
            try {
                dao.remove(service)
            } catch (e: SQLiteConstraintException) {
                failure = true
                inFailureCase()
            } finally {
                if (!failure) {
                    inSuccessCase()
                }
            }
        }
    }

    fun listAll() = dao.listAll()

    fun returnDescriptionForId(id: String) = dao.returnDescriptionForId(id)

    fun returnForId(id: String) = dao.returnForId(id)
}