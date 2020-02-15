package com.example.workdate.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Client(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val phone: String,
    val address: String
)