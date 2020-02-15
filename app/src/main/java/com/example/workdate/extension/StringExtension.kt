package com.example.workdate.extension

import java.util.*

fun String.limit(characters: Int): String {
    if (this.length > characters) {
        val firstCharacter = 0
        return "${this.substring(firstCharacter, characters)}..."
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