package com.br.workdate.extension

import com.br.workdate.exception.NegativeLimitInStringException
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class StringExtensionTest() {

    private val message = "test test test"
    private val uuid = UUID.randomUUID().toString()

    @Test
    fun returnLimitCharactersWhenMaxIsFive() {
        val messageReturned = message.limit(5)
        assertEquals("test ...", messageReturned)
    }

    @Test
    fun returnLimitWhenHaveTenLimits() {
        val limitReturned = message.limit(10)
        assertEquals(13, limitReturned.length)
    }

    @Test
    fun returnLimitWhenMessageSurpassesLimit() {
        val limitReturned = message.limit(Int.MAX_VALUE)
        assertEquals(message.length, limitReturned.length)
    }

    @Test
    fun returnLimitCharactersWhenMaxIsFourteen() {
        val messageReturned = message.limit(13)
        assertEquals("test test tes...", messageReturned)
    }

    @Test
    fun returnMessageWhenLimitSurpassesMessage() {
        val messageReturned = message.limit(Int.MAX_VALUE)
        assertEquals("test test test", messageReturned)
        assertEquals(message, messageReturned)
    }

    @Test(expected = NegativeLimitInStringException::class)
    fun returnExceptionWhenHaveNegativeLimitInString() {
        message.limit(Int.MIN_VALUE)
    }

    @Test
    fun returnUUIDWhenHaveUUID() {
        val uuidReturned = uuid.returnUUID()
        assertEquals(uuid, uuidReturned)
    }
}