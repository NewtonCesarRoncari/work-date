package com.br.workdate.extension

import android.content.Context
import com.br.workdate.R
import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.*

fun BigDecimal.formatCoin(context: Context): String {
    val brazilianFormat = DecimalFormat
        .getCurrencyInstance(Locale("pt", "br"))
    return brazilianFormat
        .format(this).replace(
            "R$",
            context.getString(R.string.pre_coin) + " "
        ) + context.getString(R.string.pos_coin)
}

fun <T> Iterable<T>.sumByBigDecimal(selector: (T) -> BigDecimal): BigDecimal {
    var sum = BigDecimal.ZERO
    for (element in this) {
        sum += selector(element)
    }
    return sum
}