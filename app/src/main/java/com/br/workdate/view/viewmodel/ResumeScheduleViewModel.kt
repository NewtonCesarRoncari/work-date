package com.br.workdate.view.viewmodel

import com.br.workdate.model.Schedule
import com.br.workdate.repository.ResumeScheduleRepository

class ResumeScheduleViewModel(schedules: MutableList<Schedule>) {

    private val repository = ResumeScheduleRepository(schedules)

    val totalOpen get() = repository.totalOpen

    val totalConcluded get() = repository.totalConcluded

    val total get() = repository.total

    fun returnPercentage(total: Int, qtt: Int): Int {
        return (qtt*100)/total
    }
}