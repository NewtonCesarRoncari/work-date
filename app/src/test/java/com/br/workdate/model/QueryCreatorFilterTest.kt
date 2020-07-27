package com.br.workdate.model

import org.junit.Assert.assertEquals
import org.junit.Test

class QueryCreatorFilterTest {

    private val queryCreatorFilterTest = QueryCreatorFilter()

    @Test
    fun returnSQLByParamsWhenNoHaveParams() {
        val params = hashMapOf<String, String>()
        assertEquals(
            "SELECT * FROM SCHEDULE ORDER BY date, hour",
            queryCreatorFilterTest.returnByParams(params, "SCHEDULE")
        )
    }

    @Test
    fun returnSQLByParamsWhenHaveNameClientParam() {
        val params = hashMapOf(@Params CLIENT_NAME to "test")
        assertEquals(
            "SELECT * FROM SCHEDULE WHERE clientName = 'test' ORDER BY date, hour",
            queryCreatorFilterTest.returnByParams(params, "SCHEDULE")
        )
    }

    @Test
    fun returnSQLByParamsWhenHaveServiceDescriptionParam() {
        val params = hashMapOf(@Params SERVICE_DESCRIPTION to "serviceTest")
        assertEquals(
            "SELECT * FROM SCHEDULE WHERE serviceDescription = 'serviceTest' ORDER BY date, hour",
            queryCreatorFilterTest.returnByParams(params, "SCHEDULE")
        )
    }

    @Test
    fun returnSQLByParamsWhenHaveNameClientAndServiceDescriptionParam() {
        val params = hashMapOf(
            @Params CLIENT_NAME to "test",
            @Params SERVICE_DESCRIPTION to "serviceTest"
        )
        assertEquals(
            "SELECT * FROM SCHEDULE WHERE clientName = 'test' AND" +
                    " serviceDescription = 'serviceTest' ORDER BY date, hour",
            queryCreatorFilterTest.returnByParams(params, "SCHEDULE")
        )
    }

    @Test
    fun returnSQLByParamsWhenHaveServiceDescriptionAndNameClientParam() {
        val params = hashMapOf(
            @Params SERVICE_DESCRIPTION to "serviceTest",
            @Params CLIENT_NAME to "test"
        )
        assertEquals(
            "SELECT * FROM SCHEDULE WHERE clientName = 'test' AND" +
                    " serviceDescription = 'serviceTest' ORDER BY date, hour",
            queryCreatorFilterTest.returnByParams(params, "SCHEDULE")
        )
    }

    @Test
    fun returnSQLByParamsWhenHaveNameClientAndServiceDescriptionAndCanceledParam() {
        val params = hashMapOf(
            @Params CLIENT_NAME to "test",
            @Params SERVICE_DESCRIPTION to "serviceTest",
            @Params CANCELED to "1"
        )
        assertEquals(
            "SELECT * FROM SCHEDULE WHERE canceled = '1' AND" +
                    " serviceDescription = 'serviceTest' AND" +
                    " clientName = 'test' ORDER BY date, hour",
            queryCreatorFilterTest.returnByParams(params, "SCHEDULE")
        )
    }

    @Test
    fun returnSQLByParamsWhenHaveServiceDescriptionAndNameClientAndNoCanceledParam() {
        val params = hashMapOf(
            @Params SERVICE_DESCRIPTION to "serviceTest",
            @Params CLIENT_NAME to "test",
            @Params CANCELED to "0"
        )
        assertEquals(
            "SELECT * FROM SCHEDULE WHERE canceled = '0' AND" +
                    " serviceDescription = 'serviceTest' AND" +
                    " clientName = 'test' ORDER BY date, hour",
            queryCreatorFilterTest.returnByParams(params, "SCHEDULE")
        )
    }

    @Test
    fun returnSQLByParamsWhenHaveFinishedAndServiceDescriptionAndNameClientParam() {
        val params = hashMapOf(
            @Params SERVICE_DESCRIPTION to "serviceTest",
            @Params CLIENT_NAME to "test",
            @Params FINISHED to "1"
        )
        assertEquals(
            "SELECT * FROM SCHEDULE WHERE serviceDescription = 'serviceTest' AND" +
                    " finished = '1' AND" +
                    " clientName = 'test' ORDER BY date, hour",
            queryCreatorFilterTest.returnByParams(params, "SCHEDULE")
        )
    }
}