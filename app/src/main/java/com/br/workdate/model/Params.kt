package com.br.workdate.model

import androidx.annotation.StringDef

@Target(
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.EXPRESSION
)
@Retention(AnnotationRetention.SOURCE)
@StringDef(CLIENT_NAME, SERVICE_DESCRIPTION, FROM_DATE, TO_DATE, SITUATION)
annotation class Params

const val CLIENT_NAME = "clientName"
const val SERVICE_DESCRIPTION = "serviceDescription"
const val FROM_DATE = "from_date"
const val TO_DATE = "to_date"
const val SITUATION = "situation"