package com.br.workdate.database.converter

import androidx.room.TypeConverter
import java.math.BigDecimal

class BigDecimalConverter {

    @TypeConverter
    fun forBigDecimal(value: BigDecimal?): String {
        return value?.toString() ?: ""
    }

    @TypeConverter
    fun forString(value: String?): BigDecimal {
        return value?.let { BigDecimal(it) } ?: BigDecimal.ZERO
    }
}