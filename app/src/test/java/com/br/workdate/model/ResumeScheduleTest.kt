package com.br.workdate.model

import com.br.workdate.resources.ScheduleResources
import org.junit.Assert.assertEquals
import org.junit.Test

class ResumeScheduleTest {

    private val schedules = ScheduleResources.listSchedule

    @Test
    fun getTotalOpen() {
        val resumeSchedule = ResumeSchedule(schedules)
        assertEquals(6, resumeSchedule.totalOpen)
    }

    @Test
    fun getTotalConcluded() {
        val resumeSchedule = ResumeSchedule(schedules)
        assertEquals(2, resumeSchedule.totalConcluded)
    }

    @Test
    fun getTotal() {
        val resumeSchedule = ResumeSchedule(schedules)
        assertEquals(8, resumeSchedule.total)
    }

    @Test
    fun returnSizeOfSchedules() {
        assertEquals(8, schedules.size)
    }
}