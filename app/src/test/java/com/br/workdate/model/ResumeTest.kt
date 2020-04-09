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

    private val resume = Resume(releases)

    @Test
    fun getTotalOpen() {
        assertEquals(BigDecimal("9.4"), resume.totalOpen)
    }

    @Test
    fun getTotalPaid() {
        assertEquals(BigDecimal("15"), resume.totalPaid)
    }

    @Test
    fun getTotal() {
        assertEquals(BigDecimal("24.4"), resume.total)
    }

    @Test(expected = NegativeValueException::class)
    fun negative() {
        val releases = mutableListOf(
            Release(
                "",
                "client",
                "service",
                BigDecimal("-10"),
                Calendar.getInstance().time,
                Calendar.getInstance().time,
                Situation.OPEN,
                ""
            )
        )
        val resume = Resume(releases)
        resume.total
    }
}