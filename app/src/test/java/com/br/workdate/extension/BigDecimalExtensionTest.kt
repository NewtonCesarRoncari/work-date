package com.br.workdate.extension

import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal

class BigDecimalExtensionTest() {

    @Test
    fun returnFormatForBrazilianCoin() {
        val stringReturned = BigDecimal("10.5").formatForBrazilianCoin()
        assertEquals(stringReturned, "R$  10,50")
    }
}