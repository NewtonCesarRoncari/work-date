package com.br.workdate.view.viewmodel

import androidx.lifecycle.ViewModel
import com.br.workdate.model.Client
import com.br.workdate.repository.ClientRepository

class ClientViewModel(private val repository: ClientRepository) : ViewModel() {

    fun insert(client: Client) = repository.insert(client)

    fun update(client: Client) = repository.update(client)

    fun remove(
        client: Client,
        inFailureCase: () -> Unit,
        inSuccessCase: () -> Unit
    ) = repository.remove(client, inFailureCase, inSuccessCase)

    fun listAll() = repository.listAll()

    fun getNameForId(id: String) = repository.getNameForId(id)

    fun returnForId(id: String) = repository.returnForId(id)
}