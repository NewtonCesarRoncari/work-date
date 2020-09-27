package com.br.workdate.model

class ResumeSchedule(private val schedules: MutableList<Schedule>) {

    val totalOpen get() = filterBy(finished = false)

    val totalConcluded get() = filterBy(finished = true)

    val total get() = totalOpen.plus(totalConcluded)

    private fun filterBy(finished: Boolean): Int {
        return schedules.filter { schedule -> schedule.finished == finished }.size
    }
}