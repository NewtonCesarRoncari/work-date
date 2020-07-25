package com.br.workdate.model

import org.junit.Assert.assertEquals
import org.junit.Test

class QueryCreatorFilterTest {

    private val queryCreatorFilterTest = QueryCreatorFilter()

    @Test
    fun returnSQLByParamsWhenNoHaveParams() {
        val params = hashMapOf<String, String>()
        assertEquals(
            "SELECT * FROM SCHEDULE",
            queryCreatorFilterTest.returnByParams(params)
        )
    }

    @Test
    fun returnSQLByParamsWhenHaveNameClientParam() {
        val params = hashMapOf(@Params CLIENT_NAME to "test")
        assertEquals(
            "SELECT * FROM SCHEDULE WHERE clientName = test",
            queryCreatorFilterTest.returnByParams(params)
        )
    }

    @Test
    fun returnSQLByParamsWhenHaveServiceDescriptionParam() {
        val params = hashMapOf(@Params SERVICE_DESCRIPTION to "serviceTest")
        assertEquals(
            "SELECT * FROM SCHEDULE WHERE serviceDescription = serviceTest",
            queryCreatorFilterTest.returnByParams(params)
        )
    }

    @Test
    fun returnSQLByParamsWhenHaveNameClientAndServiceDescriptionParam() {
        val params = hashMapOf(
            @Params CLIENT_NAME to "test",
            @Params SERVICE_DESCRIPTION to "serviceTest"
        )
        assertEquals(
            "SELECT * FROM SCHEDULE WHERE clientName " +
                    "= test AND serviceDescription = serviceTest",
            queryCreatorFilterTest.returnByParams(params)
        )
    }

    @Test
    fun returnSQLByParamsWhenHaveServiceDescriptionAndNameClientParam() {
        val params = hashMapOf(
            @Params SERVICE_DESCRIPTION to "serviceTest",
            @Params CLIENT_NAME to "test"
        )
        assertEquals(
            "SELECT * FROM SCHEDULE WHERE clientName " +
                    "= test AND serviceDescription = serviceTest",
            queryCreatorFilterTest.returnByParams(params)
        )
    }

    @Test
    fun returnSQLByParamsWhenHaveNameClientAndServiceDescriptionAndValueParam() {
        val params = hashMapOf(
            @Params CLIENT_NAME to "test",
            @Params SERVICE_DESCRIPTION to "serviceTest"
        )
        assertEquals(
            "SELECT * FROM SCHEDULE WHERE clientName " +
                    "= test AND serviceDescription = serviceTest AND value = 10",
            queryCreatorFilterTest.returnByParams(params)
        )
    }

    @Test
    fun returnSQLByParamsWhenHaveServiceDescriptionAndNameClientAndValueParam() {
        val params = hashMapOf(
            @Params SERVICE_DESCRIPTION to "serviceTest",
            @Params CLIENT_NAME to "test"
        )
        assertEquals(
            "SELECT * FROM SCHEDULE WHERE clientName " +
                    "= test AND serviceDescription = serviceTest AND value = 10",
            queryCreatorFilterTest.returnByParams(params)
        )
    }

    @Test
    fun returnSQLByParamsWhenHaveValueAndServiceDescriptionAndNameClientParam() {
        val params = hashMapOf(
            @Params SERVICE_DESCRIPTION to "serviceTest",
            @Params CLIENT_NAME to "test"
        )
        assertEquals(
            "SELECT * FROM SCHEDULE WHERE clientName " +
                    "= test AND serviceDescription = serviceTest AND value = 10",
            queryCreatorFilterTest.returnByParams(params)
        )
    }

    @Test
    fun returnSQLByParamsWhenHaveAllParams() {
        val params = hashMapOf(
            @Params SERVICE_DESCRIPTION to "serviceTest",
            @Params CLIENT_NAME to "test",
            @Params SITUATION to "1"
        )
        assertEquals(
            "SELECT * FROM SCHEDULE WHERE clientName = test AND" +
                    " serviceDescription = serviceTest AND" +
                    " value = 10 AND" +
                    " date = 18/10/2020 AND" +
                    " hour = 22:30 AND" +
                    " situation = 1",
            queryCreatorFilterTest.returnByParams(params)
        )
    }
}