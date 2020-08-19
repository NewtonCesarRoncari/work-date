package com.br.workdate.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(indices = [Index(value = ["name"], unique = true)])
class Client(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val phone: String,
    val address: String
) : Serializable