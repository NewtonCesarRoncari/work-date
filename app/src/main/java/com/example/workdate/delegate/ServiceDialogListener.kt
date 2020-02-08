package com.example.workdate.delegate

import com.example.workdate.model.Service

interface ServiceDialogListener {
    fun listener(service: Service)
}