package com.br.workdate.view.recyclerview.adapter

import android.content.Context
import com.br.workdate.model.Client
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.verify
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ClientAdapterTest {

    private val context = Mockito.mock(Context::class.java)

    @Spy
    private val adapter = ClientAdapter(
        context, mutableListOf(
            Client("", "client1", "", ""),
            Client("", "client2", "", "")
        )
    )

    @Test
    fun returnSizeOfAdapterWhenHaveTwoClients() {
        assertEquals(2, adapter.itemCount)
    }

    @Test
    fun returnSizeWhenRemoveItemOfAdapter() {
        doNothing().`when`(adapter).notifyItemRemoved(1)

        val size = adapter.itemCount
        adapter.remove(1)
        val sizeAfterRemoved = size - 1
        verify(adapter).notifyItemRemoved(1)

        assertEquals(sizeAfterRemoved, adapter.itemCount)
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun returnExceptionWhenItemNotHaveInList() {
        adapter.remove(3)
    }
}