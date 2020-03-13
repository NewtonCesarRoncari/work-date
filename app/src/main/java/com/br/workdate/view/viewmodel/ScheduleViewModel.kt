package com.br.workdate.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.br.workdate.model.Schedule
import com.br.workdate.model.Situation
import com.br.workdate.repository.ScheduleRepository

class ScheduleViewModel(private val repository: ScheduleRepository) : ViewModel() {

    fun insert(schedule: Schedule) = repository.insert(schedule)

    fun update(schedule: Schedule) = repository.update(schedule)

    fun remove(schedule: Schedule) = repository.remove(schedule)

    fun listAll(): LiveData<MutableList<Schedule>> = repository.listAll()

    fun checkFinished(finished: Boolean): Situation {
        return if (finished) {
            Situation.PAID
        } else {
            Situation.OPEN
        }
    }
}