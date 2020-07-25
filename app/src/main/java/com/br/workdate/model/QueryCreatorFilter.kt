package com.br.workdate.model

class QueryCreatorFilter() {

    fun returnByParams(params: HashMap<String, String>, table: String): String {

        var sql = "SELECT * FROM $table"

        if (params.isNotEmpty()) {
            sql += " WHERE "
        }
        if (params.containsKey(@Params CLIENT_NAME)) {
            sql += "${@Params CLIENT_NAME} = ${params[@Params CLIENT_NAME]} AND "
        }
        if (params.containsKey(@Params SERVICE_DESCRIPTION)) {
            sql += "${@Params SERVICE_DESCRIPTION} = ${params[@Params SERVICE_DESCRIPTION]} AND "
        }
        if (params.containsKey(@Params FROM_DATE)) {
            sql += "date >= ${params[@Params FROM_DATE]} AND "
        }
        if (params.containsKey(@Params TO_DATE)) {
            sql += "date <= ${params[@Params TO_DATE]} AND "
        }
        if (params.containsKey(@Params SITUATION)) {
            sql += "${@Params SITUATION} = ${params[@Params SITUATION]} AND "
        }
        if (params.containsKey(@Params FINISHED)) {
            sql += "${@Params FINISHED} = ${params[@Params FINISHED]} AND "
        }
        if (params.containsKey(@Params CANCELED)) {
            sql += "${@Params CANCELED} = ${params[@Params CANCELED]} AND "
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