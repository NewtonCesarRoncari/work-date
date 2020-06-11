package com.br.workdate.extension

import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.*

fun BigDecimal.formatForBrazilianCoin(): String {
    val brazilianFormat = DecimalFormat
        .getCurrencyInstance(Locale("pt", "br"))
    return brazilianFormat
        .format(this).replace("R$", "R$ ")
}

fun <T> Iterable<T>.sumByBigDecimal(selector: (T) -> BigDecimal): BigDecimal {
    var sum = BigDecimal.ZERO
    for (element in this) {
        sum += selector(element)
    }
    return sum
}