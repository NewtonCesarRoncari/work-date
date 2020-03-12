package com.br.workdate.database.converter

import androidx.room.TypeConverter
import com.br.workdate.model.Type
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
    fun typeForLong(type: Type): Int? {
        return if (type == Type.REVENUE) {
            1
        } else {
            0
        }
    }

    @TypeConverter
    fun longForType(int: Int): Type? {
        return if (int == 1) {
            Type.REVENUE
        } else {
            Type.EXPENSE
        }
    }
}