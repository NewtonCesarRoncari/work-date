package com.br.workdate.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.util.*

@Entity(
    foreignKeys = [ForeignKey(
        entity = Client::class,
        parentColumns = ["id"],
        childColumns = ["client_id"],
        onUpdate = CASCADE,
        onDelete = CASCADE
    ),
        ForeignKey(
            entity = Service::class,
            parentColumns = ["id"],
            childColumns = ["service_id"],
            onUpdate = CASCADE,
            onDelete = CASCADE
        )],
    ignoredColumns = ["service", "client"]
)
class Schedule(
    @PrimaryKey(autoGenerate = false)
    private val id: String,
    private val date: Date,
    private val hour: Date,
    private val value: BigDecimal,
    private val canceled: Boolean,
    private val finished: Boolean,
    private val observation: String,
    @ColumnInfo(name = "service_id")
    private val serviceId: String,
    @ColumnInfo(name = "client_id")
    private val clientId: String,
    private val service: Service,
    private val client: Client
)