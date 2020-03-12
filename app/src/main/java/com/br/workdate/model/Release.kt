package com.br.workdate.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.ForeignKey.RESTRICT
import androidx.room.Index
import java.math.BigDecimal
import java.util.*

@Entity(
    foreignKeys = [ForeignKey(
        entity = Schedule::class,
        parentColumns = ["id"],
        childColumns = ["scheduleId"],
        onUpdate = CASCADE,
        onDelete = RESTRICT
    )],
    indices = [Index("schedule_id")]
)
class Release(
    val id: String,
    val clientName: String,
    val serviceDescription: String,
    val value: BigDecimal,
    val date: Date,
    val hour: Date,
    val type: Type,
    @ColumnInfo(name = "schedule_id")
    val scheduleId: String
)