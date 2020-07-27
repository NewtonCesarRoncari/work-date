package com.br.workdate.model

class QueryCreatorFilter() {

    fun returnByParams(params: HashMap<String, String>, table: String): String {

        var query = "SELECT * FROM $table"

        if (params.isNotEmpty()) {
            query += " WHERE "
        }
        if (params.containsKey(@Params CLIENT_NAME)) {
            query += "${@Params CLIENT_NAME} = ${params[@Params CLIENT_NAME]} AND "
        }
        if (params.containsKey(@Params SERVICE_DESCRIPTION)) {
            query += "${@Params SERVICE_DESCRIPTION} = ${params[@Params SERVICE_DESCRIPTION]} AND "
        }
        if (params.containsKey(@Params FROM_DATE)) {
            query += "date >= ${params[@Params FROM_DATE]} AND "
        }
        if (params.containsKey(@Params TO_DATE)) {
            query += "date <= ${params[@Params TO_DATE]} AND "
        }
        if (params.containsKey(@Params SITUATION)) {
            query += "${@Params SITUATION} = ${params[@Params SITUATION]} AND "
        }
        if (params.containsKey(@Params FINISHED)) {
            query += "${@Params FINISHED} = ${params[@Params FINISHED]} AND "
        }
        if (params.containsKey(@Params CANCELED)) {
            query += "${@Params CANCELED} = ${params[@Params CANCELED]} AND "
        }
        return removeLastAnd(query, params)
    }

    private fun removeLastAnd(query: String, params: HashMap<String, String>): String {
        return if (params.isNotEmpty()) {
            query.substring(0, query.length - 5) + " ORDER BY date, hour"
        } else "$query ORDER BY date, hour"
    }
}