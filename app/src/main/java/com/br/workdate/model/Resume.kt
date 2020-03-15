package com.br.workdate.model

import com.br.workdate.extension.sumByBigDecimal

class Resume(private val releases: MutableList<Release>) {

    val totalOpen get() = sumBy(Situation.OPEN)

    val totalPaid get() = sumBy(Situation.PAID)

    val total get() = totalOpen.plus(totalPaid)

    private fun sumBy(situation: Situation) =
        releases.filter { release -> release.situation == situation }
            .sumByBigDecimal { release -> release.value }
}