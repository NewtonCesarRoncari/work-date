package com.br.workdate.repository

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.lifecycle.LiveData
import com.br.workdate.database.dao.ClientDAO
import com.br.workdate.model.Client
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

    fun remove(client: Client) {
        scope.launch {
            try {
                dao.remove(client)
            } catch (e: SQLiteConstraintException) {
                Log.e("remove", e.message!!)
            }
        }
    }

    fun listAll() = dao.listAll()

    fun getNameForId(id: String): LiveData<String> {
        return dao.returnNameForId(id)
    }

    fun returnForId(id: String): LiveData<Client> {
        return dao.returnForId(id)
    }
}