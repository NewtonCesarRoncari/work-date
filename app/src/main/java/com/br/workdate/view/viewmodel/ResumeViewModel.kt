package com.br.workdate.view.viewmodel

import com.br.workdate.model.Release
import com.br.workdate.model.Situation
import java.math.BigDecimal

class ResumeViewModel(private val releases: MutableList<Release>) {

    val totalOpen get() = sumBy(Situation.OPEN)

    val totalPaid get() = sumBy(Situation.PAID)

    val total get() = totalOpen.plus(totalPaid)

    private fun sumBy(situation: Situation) = BigDecimal(
        releases
            .filter { release -> release.situation == situation }
            .sumByDouble { release -> release.value.toDouble() })
}