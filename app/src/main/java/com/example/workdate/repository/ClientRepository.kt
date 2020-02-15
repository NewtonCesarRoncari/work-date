package com.example.workdate.repository

import androidx.lifecycle.LiveData
import com.example.workdate.database.dao.ClientDAO
import com.example.workdate.model.Client
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ClientRepository(private val dao: ClientDAO) {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    fun insert(client: Client) {
        scope.launch {
            dao.insert(client)
        }
    }

    fun update(client: Client) {
        scope.launch {
            dao.update(client)
        }
    }

    fun remove(client: Client) = dao.remove(client)

    fun listAll(): LiveData<MutableList<Client>> = dao.listAll()
}