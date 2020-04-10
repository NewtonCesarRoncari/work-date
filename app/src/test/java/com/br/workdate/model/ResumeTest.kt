package com.br.workdate.model

import com.br.workdate.exception.NegativeValueException
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal
import java.util.*

class ResumeTest {

    private val releases = mutableListOf(
        Release(
            "",
            "client",
            "service",
            BigDecimal("5.1"),
            Calendar.getInstance().time,
            Calendar.getInstance().time,
            Situation.OPEN,
            ""
        ),
        Release(
            "",
            "client",
            "service",
            BigDecimal("4.3"),
            Calendar.getInstance().time,
            Calendar.getInstance().time,
            Situation.OPEN,
            ""
        ),
        Release(
            "",
            "client",
            "service",
            BigDecimal("4"),
            Calendar.getInstance().time,
            Calendar.getInstance().time,
            Situation.PAID,
            ""
        ),
        Release(
            "",
            "client",
            "service",
            BigDecimal("5"),
            Calendar.getInstance().time,
            Calendar.getInstance().time,
            Situation.PAID,
            ""
        ),
        Release(
            "",
            "client",
            "service",
            BigDecimal("6"),
            Calendar.getInstance().time,
            Calendar.getInstance().time,
            Situation.PAID,
            ""
        )
    )
    private val releasesForException = mutableListOf(
        Release(
            "",
            "client",
            "service",
            BigDecimal("-10"),
            Calendar.getInstance().time,
            Calendar.getInstance().time,
            Situation.OPEN,
            ""
        ),
        Release(
            "",
            "client",
            "service",
            BigDecimal("-10"),
            Calendar.getInstance().time,
            Calendar.getInstance().time,
            Situation.PAID,
            ""
        )
    )

    @Test
    fun getTotalOpen() {
        assertEquals(BigDecimal("9.4"), Resume(releases).totalOpen)
    }

    @Test
    fun getTotalPaid() {
        assertEquals(BigDecimal("15"), Resume(releases).totalPaid)
    }

    @Test
    fun getTotal() {
        assertEquals(BigDecimal("24.4"), Resume(releases).total)
    }

    @Test(expected = NegativeValueException::class)
    fun returnExceptionWhenNegativeValueInTotal() {
        Resume(releasesForException).total
    }

    @Test(expected = NegativeValueException::class)
    fun returnExceptionWhenNegativeValueInTotalOpen() {
        Resume(releasesForException).totalOpen
    }

    @Test(expected = NegativeValueException::class)
    fun returnExceptionWhenNegativeValueInTotalPaid() {
        Resume(releasesForException).totalPaid
    }

    @Test(expected = NegativeValueException::class)
    fun returnExceptionWhenHaveReleaseWithNegativeValue() {
        releases.add(
            Release(
                "",
                "client",
                "service",
                BigDecimal("-10"),
                Calendar.getInstance().time,
                Calendar.getInstance().time,
                Situation.PAID,
                ""
            )
        )
        Resume(releases).total
    }
}