package com.br.workdate.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable
import java.math.BigDecimal

@Entity(indices = [Index(value = ["description"], unique = true)])
class Service(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val description: String,
    val value: BigDecimal
) : Serializable