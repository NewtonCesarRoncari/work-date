package com.br.workdate.database.converter

import com.br.workdate.exception.ConverterException
import com.br.workdate.model.Situation.OPEN
import com.br.workdate.model.Situation.PAID
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal

class ConvertersTest() {

    private val converters = Converters()

    @Test
    fun returnStringForBigDecimal() {
        val stringReturned = converters.forBigDecimal(BigDecimal("10.5"))
        assertEquals(stringReturned, "10.5")
    }

    @Test
    fun returnBigDecimalForString() {
        val bigDecimalReturned = converters.forString("10")
        assertEquals(bigDecimalReturned, BigDecimal.TEN)
    }

    @Test
    fun returnTypeSituationForLongWhenLongIsOne() {
        val situationReturned = converters.longForType(0)
        assertEquals(situationReturned, OPEN)
    }

    @Test
    fun returnTypeSituationForLongWhenLongIsTwo() {
        val situationReturned = converters.longForType(1)
        assertEquals(situationReturned, PAID)
    }

    @Test(expected = ConverterException::class)
    fun returnExceptionTypeSituationForLongWhenLongIsNotInEnumLongIs3() {
        converters.longForType(Int.MAX_VALUE)
    }

    @Test(expected = ConverterException::class)
    fun returnExceptionTypeSituationForLongWhenLongIsNotInEnumLongIs0() {
        converters.longForType(Int.MIN_VALUE)
    }

    @Test
    fun returnIntForSituationWhenSituationIsOpen() {
        val intReturned = converters.typeForLong(OPEN)
        assertEquals(intReturned, 0)
    }

    @Test
    fun returnIntForSituationWhenSituationIsPaid() {
        val intReturned = converters.typeForLong(PAID)
        assertEquals(intReturned, 1)
    }
}