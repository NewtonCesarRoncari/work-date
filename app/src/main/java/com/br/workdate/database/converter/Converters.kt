package com.br.workdate.database.converter

import androidx.room.TypeConverter
import com.br.workdate.exception.ConverterException
import com.br.workdate.model.Situation
import com.br.workdate.model.Situation.OPEN
import com.br.workdate.model.Situation.PAID
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
        return if (situation == OPEN) 0 else 1
    }

    @TypeConverter
    fun longForType(int: Int): Situation? {
        checkIfHaveSituationForExp(int)
        return if (int == 0) OPEN else PAID
    }

    private fun checkIfHaveSituationForExp(int: Int) {
        if (int < 0 || int > 1) {
            throw ConverterException()
        }
    }
}