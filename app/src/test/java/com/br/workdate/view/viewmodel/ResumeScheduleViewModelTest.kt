package com.br.workdate.view.viewmodel

import com.br.workdate.exception.NegativeValueException
import com.br.workdate.resources.ScheduleResources
import org.junit.Assert.assertEquals
import org.junit.Test

class ResumeScheduleViewModelTest {

    private val schedules = ScheduleResources.listSchedule

    @Test
    fun returnPercentage() {
        val resumeScheduleViewModel = ResumeScheduleViewModel(schedules)
        assertEquals(66, resumeScheduleViewModel.returnPercentage(12, 8))
    }

    @Test
    fun returnPercentage2() {
        val resumeScheduleViewModel = ResumeScheduleViewModel(schedules)
        assertEquals(2, resumeScheduleViewModel.returnPercentage(44, 1))
    }

    @Test
    fun returnPercentage3() {
        val resumeScheduleViewModel = ResumeScheduleViewModel(schedules)
        assertEquals(700, resumeScheduleViewModel.returnPercentage(7, 49))
    }

    @Test
    fun returnPercentageWhenZeroTotal() {
        val resumeScheduleViewModel = ResumeScheduleViewModel(schedules)
        assertEquals(0, resumeScheduleViewModel.returnPercentage(0, 3))
    }

    @Test
    fun returnPercentageWhenZeroQtt() {
        val resumeScheduleViewModel = ResumeScheduleViewModel(schedules)
        assertEquals(0, resumeScheduleViewModel.returnPercentage(3, 0))
    }

    @Test
    fun returnPercentageWhenZeroTotalAndZeroQtt() {
        val resumeScheduleViewModel = ResumeScheduleViewModel(schedules)
        assertEquals(0, resumeScheduleViewModel.returnPercentage(0, 0))
    }

    @Test(expected = NegativeValueException::class)
    fun returnExceptionPercentageWhenNegativeTotal() {
        ResumeScheduleViewModel(schedules).returnPercentage(-7, 2)
    }

    @Test(expected = NegativeValueException::class)
    fun returnExceptionPercentageWhenNegativeQttl() {
        ResumeScheduleViewModel(schedules).returnPercentage(9, -4)
    }

    @Test(expected = NegativeValueException::class)
    fun returnExceptionPercentageWhenNegativeTotalAndNegativeQtt() {
        ResumeScheduleViewModel(schedules).returnPercentage(-7, -2)
    }
}