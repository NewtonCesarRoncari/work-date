package com.br.workdate.repository

import com.br.workdate.database.dao.ClientDAO
import com.br.workdate.database.dao.ServiceDAO

class FilterRepository(
    private val clientDAO: ClientDAO,
    private val serviceDAO: ServiceDAO
) {
    fun returnAllClientNames() = clientDAO.returnAllClientNames()

    fun returnAllServicesDescriptions() = serviceDAO.returnAllServicesDescriptions()
}