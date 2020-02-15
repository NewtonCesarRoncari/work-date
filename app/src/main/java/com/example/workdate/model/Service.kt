package com.example.workdate.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity
class Service(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val description: String,
    val value: BigDecimal
)