package com.br.workdate.model

class QueryCreatorFilter() {

    fun returnByParams(params: HashMap<String, String>, table: String): String {

        var query = "SELECT * FROM $table"

        if (params.isNotEmpty()) {
            query += " WHERE "
            params.forEach { map ->

                if (map.key == @Params FROM_DATE) {
                    query += "date >= '${map.value}' AND "
                }
                if (map.key == @Params TO_DATE) {
                    query += "date <= '${map.value}' AND "
                }

                if (map.key != @Params FROM_DATE && map.key != @Params TO_DATE) {
                    query += "${map.key} = '${map.value}' AND "
                }
            }
        }
        return removeLastAnd(query, params)
    }

    private fun removeLastAnd(query: String, params: HashMap<String, String>): String {
        return if (params.isNotEmpty()) {
            query.substring(0, query.length - 5) + " ORDER BY date, hour"
        } else "$query ORDER BY date, hour"
    }
}