package com.br.workdate.repository

import androidx.lifecycle.LiveData
import androidx.sqlite.db.SimpleSQLiteQuery
import com.br.workdate.database.dao.ScheduleDAO
import com.br.workdate.model.Schedule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ScheduleRepository(private val dao: ScheduleDAO) {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    fun insert(schedule: Schedule) = scope.launch { dao.insert(schedule) }

    fun update(schedule: Schedule) = scope.launch { dao.update(schedule) }

    fun remove(schedule: Schedule) = scope.launch { dao.remove(schedule) }

    fun listAll() = dao.listAll()

    fun listSchedulesInMonthNoFinished(
        firstDayMonth: Long,
        lastDayMonth: Long
    ) = dao.listSchedulesInMonthNoFinished(firstDayMonth, lastDayMonth)

    fun findScheduleFilter(query: String): LiveData<MutableList<Schedule>> {
        val simpleSQLiteQuery = SimpleSQLiteQuery(query)
        return dao.findScheduleFilter(simpleSQLiteQuery)
    }
}