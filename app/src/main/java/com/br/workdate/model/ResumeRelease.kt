package com.br.workdate.model

import com.br.workdate.exception.NegativeValueException
import com.br.workdate.extension.sumByBigDecimal
import java.math.BigDecimal

class ResumeRelease(private val releases: MutableList<Release>) {

    val totalOpen get() = sumBy(Situation.OPEN)

    val totalPaid get() = sumBy(Situation.PAID)

    val total get() = totalOpen.plus(totalPaid)

    private fun sumBy(situation: Situation): BigDecimal {
        return releases.filter { release -> release.situation == situation }
            .sumByBigDecimal { release ->
                if (release.value < BigDecimal.ZERO) throw NegativeValueException()
                release.value
            }
    }
}