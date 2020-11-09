package com.br.workdate.extension

import com.br.workdate.exception.NegativeLimitInStringException
import java.math.BigDecimal
import java.util.*

fun String.limit(maxCharacters: Int): String {
    if (maxCharacters < 0) throw NegativeLimitInStringException()
    if (this.length > maxCharacters) {
        val firstCharacter = 0
        return "${this.substring(firstCharacter, maxCharacters)}..."
    }
    return this
}

fun String.returnUUID(): String {
    return if (this.isEmpty()) UUID.randomUUID().toString() else this
}

fun String.percentage(): String {
    return "${this}%"
}

fun String.tryParseBigDecimal(serviceStringValue: String): BigDecimal {
    return try {
        BigDecimal(serviceStringValue)
    } catch (e: NumberFormatException) {
        BigDecimal.ZERO
    }
}