package com.br.workdate.view.databinding

import androidx.lifecycle.MutableLiveData
import com.br.workdate.extension.returnUUID
import com.br.workdate.extension.tryParseBigDecimal
import com.br.workdate.model.Service

class ServiceData(
    private var service: Service,
    val id: MutableLiveData<String> = MutableLiveData<String>().also { it.value = service.id },
    val description: MutableLiveData<String> = MutableLiveData<String>().also {
        it.value = service.description
    },
    val value: MutableLiveData<String> = MutableLiveData<String>().also {
        it.value = service.value.toString()
    },
) {

    fun returnService(): Service {
        val emptyString = ""
        val serviceId = if (id.value.isNullOrEmpty()) emptyString.returnUUID() else id.value
        return this.service.copy(
            id = serviceId!!,
            description = description.value?.trim() ?: emptyString,
            value = value.value?.tryParseBigDecimal(value.value!!)!!
        )
    }
}