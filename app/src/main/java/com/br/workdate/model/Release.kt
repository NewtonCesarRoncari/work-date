package com.br.workdate.model

import java.math.BigDecimal
import java.util.*

class Release(
    val clientName: String,
    val serviceDescription: String,
    val value: BigDecimal,
    val date: Date,
    val hour: Date,
    val type: Type
)