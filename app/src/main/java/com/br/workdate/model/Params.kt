package com.br.workdate.model

import androidx.annotation.StringDef

@Target(
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.EXPRESSION
)
@Retention(AnnotationRetention.SOURCE)
@StringDef(CLIENT_NAME, SERVICE_DESCRIPTION, VALUE, DATE, HOUR, SITUATION)
annotation class Params

const val CLIENT_NAME = "clientName"
const val SERVICE_DESCRIPTION = "serviceDescription"
const val VALUE = "value"
const val DATE = "date"
const val HOUR = "hour"
const val SITUATION = "situation"