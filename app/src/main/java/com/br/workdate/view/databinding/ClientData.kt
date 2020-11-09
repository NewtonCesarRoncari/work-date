package com.br.workdate.view.databinding

import androidx.lifecycle.MutableLiveData
import com.br.workdate.extension.returnUUID
import com.br.workdate.model.Client

class ClientData(
    private var client: Client,
    val id: MutableLiveData<String> = MutableLiveData<String>().also { it.value = client.id },
    val name: MutableLiveData<String> = MutableLiveData<String>().also { it.value = client.name },
    val phone: MutableLiveData<String> = MutableLiveData<String>().also { it.value = client.phone },
    val address: MutableLiveData<String> = MutableLiveData<String>().also {
        it.value = client.address
    },
) {

    fun returnClient(): Client {
        val emptyString = ""
        val clientId = if (id.value.isNullOrEmpty()) emptyString.returnUUID() else id.value
        return this.client.copy(
            id = clientId!!,
            name = name.value?.trim() ?: emptyString,
            phone = phone.value?.trim() ?: emptyString,
            address = address.value?.trim() ?: emptyString
        )
    }
}