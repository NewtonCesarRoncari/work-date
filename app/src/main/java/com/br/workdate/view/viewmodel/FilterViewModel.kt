package com.br.workdate.view.viewmodel

import androidx.lifecycle.ViewModel
import com.br.workdate.repository.FilterRepository

class FilterViewModel(
    private val repository: FilterRepository
) : ViewModel() {

    fun returnAllClientNames() = repository.returnAllClientNames()

    fun returnAllServicesDescriptions() = repository.returnAllServicesDescriptions()
}