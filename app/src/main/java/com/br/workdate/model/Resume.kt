package com.br.workdate.model

import com.br.workdate.exception.NegativeValueException
import com.br.workdate.extension.sumByBigDecimal
import java.math.BigDecimal

class Resume(private val releases: MutableList<Release>) {

    val totalOpen get() = sumBy(Situation.OPEN)

    val totalPaid get() = sumBy(Situation.PAID)

    val total get() = totalOpen.plus(totalPaid)

    private fun sumBy(situation: Situation): BigDecimal {
        val sumValue = releases.filter { release -> release.situation == situation }
            .sumByBigDecimal { release -> release.value }

        if (sumValue < BigDecimal.ZERO) throw NegativeValueException()
        return sumValue
    }
}