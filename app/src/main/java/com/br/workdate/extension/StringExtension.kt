package com.br.workdate.extension

import java.util.*

fun String.limit(maxCharacters: Int): String {
    if (this.length > maxCharacters) {
        val firstCharacter = 0
        return "${this.substring(firstCharacter, maxCharacters)}..."
    }
    return this
}

fun String.returnUUID(): String {
    return if (this.isEmpty()) {
        UUID.randomUUID().toString()
    } else {
        this
    }
}