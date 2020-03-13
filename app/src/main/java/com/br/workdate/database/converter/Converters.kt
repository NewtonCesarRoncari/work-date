package com.br.workdate.database.converter

import androidx.room.TypeConverter
import com.br.workdate.model.Situation
import java.math.BigDecimal
import java.util.*

class Converters {

    @TypeConverter
    fun forBigDecimal(value: BigDecimal?): String {
        return value?.toString() ?: ""
    }

    @TypeConverter
    fun forString(value: String?): BigDecimal {
        return value?.let { BigDecimal(it) } ?: BigDecimal.ZERO
    }

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun typeForLong(situation: Situation): Int? {
        return if (situation == Situation.OPEN) {
            1
        } else {
            0
        }
    }

    @TypeConverter
    fun longForType(int: Int): Situation? {
        return if (int == 1) {
            Situation.OPEN
        } else {
            Situation.PAID
        }
    }
}