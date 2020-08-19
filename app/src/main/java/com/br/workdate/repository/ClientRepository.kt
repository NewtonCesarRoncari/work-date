package com.br.workdate.repository

import android.database.sqlite.SQLiteConstraintException
import com.br.workdate.database.dao.ClientDAO
import com.br.workdate.model.Client
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ClientRepository(private val dao: ClientDAO) {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    fun insert(
        client: Client,
        inFailureCase: () -> Unit,
        inSuccessCase: () -> Unit
    ) = scope.launch {
        tryRequisition(
            requisition = { dao.insert(client) },
            inFailureCase = inFailureCase,
            inSuccessCase = inSuccessCase
        )
    }

    fun update(
        client: Client,
        inFailureCase: () -> Unit,
        inSuccessCase: () -> Unit
    ) = scope.launch {
        tryRequisition(
            requisition = { dao.update(client) },
            inFailureCase = inFailureCase,
            inSuccessCase = inSuccessCase
        )
    }

    fun remove(
        client: Client,
        inFailureCase: () -> Unit,
        inSuccessCase: () -> Unit
    ) {
        scope.launch {
            tryRequisition(
                requisition = { dao.remove(client) },
                inFailureCase = inFailureCase,
                inSuccessCase = inSuccessCase
            )
        }
    }

    fun listAll() = dao.listAll()

    fun getNameForId(id: String) = dao.returnNameForId(id)

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