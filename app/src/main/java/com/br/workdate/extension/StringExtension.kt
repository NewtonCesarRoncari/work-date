package com.br.workdate.extension

import com.br.workdate.exception.NegativeLimitInStringException
import java.util.*

fun String.limit(maxCharacters: Int): String {
    if (maxCharacters < 0) throw NegativeLimitInStringException()
    if (this.length > maxCharacters) {
        val firstCharacter = 0
        return "${this.substring(firstCharacter, maxCharacters)}..."
    }
    return this
}

fun String.booleanDatabase(): String {
    return if (this == "TRUE") "1" else "0"
}

fun String.returnUUID(): String {
    return if (this.isEmpty()) UUID.randomUUID().toString() else this
}

fun String.impalementSingleQuotes(): String {
    return "'$this'"
}