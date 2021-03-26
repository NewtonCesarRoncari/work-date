package com.br.workdate.model

import android.content.Context
import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import androidx.room.ForeignKey.RESTRICT
import com.br.workdate.extension.formatCoin
import com.br.workdate.extension.formatForBrazilianDate
import com.br.workdate.extension.formatForBrazilianHour
import java.io.Serializable
import java.math.BigDecimal
import java.util.*

@Entity(
    foreignKeys = [
        ForeignKey(
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
    val clientName: String,
    val serviceDescription: String,
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
) : Serializable {
    @Ignore
    var dateFormat: String = ""

    @Ignore
    var hourFormat: String = ""

    @Ignore
    var valueFormat: String = ""

    constructor(
        id: String,
        clientName: String,
        serviceDescription: String,
        date: Date,
        hour: Date,
        value: BigDecimal,
        canceled: Boolean,
        finished: Boolean,
        observation: String,
        serviceId: String,
        clientId: String,
        dateFormat: String,
        hourFormat: String,
        valueFormat: String
    ) : this(
        id,
        clientName,
        serviceDescription,
        date,
        hour,
        value,
        canceled,
        finished,
        observation,
        serviceId,
        clientId
    ) {
        this.dateFormat = dateFormat
        this.hourFormat = hourFormat
        this.valueFormat = valueFormat
    }

    fun makeScheduleForLayoutAdapter(context: Context): Schedule {
        return Schedule(
            this.id,
            this.clientName,
            this.serviceDescription,
            this.date,
            this.hour,
            this.value,
            this.canceled,
            this.finished,
            this.observation,
            this.serviceId,
            this.clientId,
            this.date.formatForBrazilianDate(),
            this.hour.formatForBrazilianHour(),
            this.value.formatCoin(context)
        )
    }
}