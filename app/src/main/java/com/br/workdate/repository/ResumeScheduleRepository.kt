package com.br.workdate.repository

import com.br.workdate.model.ResumeSchedule
import com.br.workdate.model.Schedule

class ResumeScheduleRepository(schedules: MutableList<Schedule>) {

    private val resume = ResumeSchedule(schedules)

    val totalOpen get() = resume.totalOpen

    val totalConcluded get() = resume.totalConcluded

    val total get() = resume.total
}