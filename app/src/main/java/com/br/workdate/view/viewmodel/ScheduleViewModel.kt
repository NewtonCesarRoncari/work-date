package com.br.workdate.view.viewmodel

import androidx.lifecycle.ViewModel
import com.br.workdate.model.Schedule
import com.br.workdate.model.Situation
import com.br.workdate.repository.ScheduleRepository

class ScheduleViewModel(private val repository: ScheduleRepository) : ViewModel() {

    fun insert(schedule: Schedule) = repository.insert(schedule)

    fun update(schedule: Schedule) = repository.update(schedule)

    fun remove(schedule: Schedule) = repository.remove(schedule)

    fun listAll() = repository.listAll()

    fun checkFinished(finished: Boolean) = if (finished) {
        Situation.PAID
    } else {
        Situation.OPEN
    }

    fun findScheduleFilter(query: String) = repository.findScheduleFilter(query)
}