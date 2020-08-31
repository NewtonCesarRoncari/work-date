package com.br.workdate.model

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.br.workdate.extension.returnUUID
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
}