package com.br.workdate.view.databinding

import androidx.databinding.ObservableField
import com.br.workdate.extension.returnUUID
import com.br.workdate.model.Client

class ClientData(
    private var client: Client,
    val id: ObservableField<String> = ObservableField(client.id),
    val name: ObservableField<String> = ObservableField(client.name),
    val phone: ObservableField<String> = ObservableField(client.phone),
    val address: ObservableField<String> = ObservableField(client.address)
) {

    fun returnClient(): Client {
        val emptyString = ""
        val clientId = if (id.get().isNullOrEmpty()) emptyString.returnUUID() else id.get()
        return this.client.copy(
            id = clientId!!,
            name = name.get()?.trim() ?: emptyString,
            phone = phone.get()?.trim() ?: emptyString,
            address = address.get()?.trim() ?: emptyString
        )
    }
}