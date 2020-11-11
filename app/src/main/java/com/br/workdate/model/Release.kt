package com.br.workdate.model

import android.content.Context
import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.br.workdate.extension.*
import java.io.Serializable
import java.math.BigDecimal
import java.util.*

@Entity(
    foreignKeys = [ForeignKey(
        entity = Schedule::class,
        parentColumns = ["id"],
        childColumns = ["schedule_id"],
        onUpdate = CASCADE,
        onDelete = CASCADE
    )],
    indices = [Index("schedule_id")]
)
class Release(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val clientName: String,
    val serviceDescription: String,
    val value: BigDecimal,
    val date: Date,
    val hour: Date,
    val situation: Situation,
    @ColumnInfo(name = "schedule_id")
    val scheduleId: String
) : Serializable {

    @Ignore
    var formatValue: String = ""

    @Ignore
    var formatDate: String = ""

    @Ignore
    var formatHour: String = ""

    constructor(schedule: Schedule, id: String = "", situation: Situation) : this(
        id.returnUUID(),
        schedule.clientName,
        schedule.serviceDescription,
        schedule.value,
        schedule.date,
        schedule.hour,
        situation,
        schedule.id
    )

    constructor(
        id: String,
        clientName: String,
        serviceDescription: String,
        value: BigDecimal,
        date: Date,
        hour: Date,
        situation: Situation,
        scheduleId: String,
        formatValue: String,
        formatDate: String,
        formatHour: String
    ) : this(id, clientName, serviceDescription, value, date, hour, situation, scheduleId) {
        this.formatValue = formatValue
        this.formatDate = formatDate
        this.formatHour = formatHour
    }

    fun makeReleaseForLayout(context: Context): Release {
        val limit = 24
        return Release(
            this.id,
            this.clientName.limit(limit),
            this.serviceDescription.limit(limit),
            this.value,
            this.date,
            this.hour,
            this.situation,
            this.scheduleId,
            this.value.formatCoin(context),
            this.date.formatForBrazilianDate(),
            this.hour.formatForBrazilianHour()
        )
    }
}