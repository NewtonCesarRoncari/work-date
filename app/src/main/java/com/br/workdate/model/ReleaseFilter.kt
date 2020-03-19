package com.br.workdate.model

class ReleaseFilter() {

    fun returnByParams(params: HashMap<String, String>): String {

        var sql = "SELECT * FROM SCHEDULE"

        if (params.isNotEmpty()) {
            sql += " WHERE "
        }
        if (params.containsKey(@Params CLIENT_NAME)) {
            sql += "${@Params CLIENT_NAME} = ${params[@Params CLIENT_NAME]} AND "
        }
        if (params.containsKey(@Params SERVICE_DESCRIPTION)) {
            sql += "${@Params SERVICE_DESCRIPTION} = ${params[@Params SERVICE_DESCRIPTION]} AND "
        }
        if (params.containsKey(@Params VALUE)) {
            sql += "${@Params VALUE} = ${params[@Params VALUE]} AND "
        }
        if (params.containsKey(@Params DATE)) {
            sql += "${@Params DATE} = ${params[@Params DATE]} AND "
        }
        if (params.containsKey(@Params HOUR)) {
            sql += "${@Params HOUR} = ${params[@Params HOUR]} AND "
        }
        if (params.containsKey(@Params SITUATION)) {
            sql += "${@Params SITUATION} = ${params[@Params SITUATION]} AND "
        }
        return removeLastAnd(sql, params)
    }

    private fun removeLastAnd(sql: String, params: HashMap<String, String>): String {
        return if (params.isNotEmpty()) {
            sql.substring(0, sql.length - 5)
        } else {
            sql
        }
    }
}