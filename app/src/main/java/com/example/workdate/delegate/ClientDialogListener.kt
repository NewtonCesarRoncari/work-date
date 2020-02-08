package com.example.workdate.delegate

import com.example.workdate.model.Client

interface ClientDialogListener {
    fun listener(client: Client)
}