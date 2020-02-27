package com.br.workdate.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.br.workdate.model.Client
import com.br.workdate.repository.ClientRepository

class ClientViewModel(private val repository: ClientRepository) : ViewModel() {

    fun insert(client: Client) = repository.insert(client)

    fun update(client: Client) = repository.update(client)

    fun remove(client: Client) = repository.remove(client)

    fun listAll(): LiveData<MutableList<Client>> = repository.listAll()

    fun getNameForId(id: String): LiveData<String> = repository.getNameForId(id)

    fun returnForId(id: String): LiveData<Client> = repository.returnForId(id)
}