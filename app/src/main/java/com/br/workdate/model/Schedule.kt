package com.br.workdate.model

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import androidx.room.ForeignKey.RESTRICT
import java.io.Serializable
import java.math.BigDecimal
import java.util.*

@Entity(
    foreignKeys = [ForeignKey(
        entity = Client::class,
        parentColumns = ["id"],
        childColumns = ["client_id"],
        onUpdate = CASCADE,
        onDelete = RESTRICT
    ),
        ForeignKey(
            entity = Service::class,
            parentColumns = ["id"],
            childColumns = ["service_id"],
            onUpdate = CASCADE,
            onDelete = RESTRICT
        )],
    indices = [
        Index("service_id"),
        Index("client_id")
    ]
)
class Schedule(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val date: Date,
    val hour: Date,
    val value: BigDecimal,
    val canceled: Boolean,
    val finished: Boolean,
    val observation: String,
    @ColumnInfo(name = "service_id")
    var serviceId: String,
    @ColumnInfo(name = "client_id")
    val clientId: String
) : Serializable