package com.example.workdate.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.workdate.model.Client
import com.example.workdate.repository.ClientRepository

class ClientViewModel(private val repository: ClientRepository) : ViewModel() {

    fun insert(client: Client) = repository.insert(client)

    fun update(client: Client) = repository.update(client)

    fun remove(client: Client) = repository.remove(client)

    fun listAll(): LiveData<MutableList<Client>> = repository.listAll()
}