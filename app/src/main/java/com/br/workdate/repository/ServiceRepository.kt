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


    fun insert(
        service: Service,
        inFailureCase: () -> Unit,
        inSuccessCase: () -> Unit
    ) {
        scope.launch {
            tryRequisition(
                requisition = { dao.insert(service) },
                inFailureCase = inFailureCase,
                inSuccessCase = inSuccessCase
            )
        }
    }

    fun update(
        service: Service,
        inFailureCase: () -> Unit,
        inSuccessCase: () -> Unit
    ) {
        scope.launch {
            tryRequisition(
                requisition = { dao.update(service) },
                inFailureCase = inFailureCase,
                inSuccessCase = inSuccessCase
            )
        }
    }

    fun remove(
        service: Service,
        inFailureCase: () -> Unit,
        inSuccessCase: () -> Unit
    ) {
        scope.launch {
            tryRequisition(
                requisition = { dao.remove(service) },
                inFailureCase = inFailureCase,
                inSuccessCase = inSuccessCase
            )
        }
    }

    fun listAll() = dao.listAll()

    fun returnDescriptionForId(id: String) = dao.returnDescriptionForId(id)

    fun returnForId(id: String) = dao.returnForId(id)

    private fun tryRequisition(
        requisition: () -> Unit,
        inFailureCase: () -> Unit,
        inSuccessCase: () -> Unit
    ) {
        var failure = false
        try {
            requisition()
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