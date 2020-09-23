package com.br.workdate.view.fragment

import android.graphics.Color
import android.view.View
import com.br.workdate.extension.formatCoin
import com.br.workdate.model.Schedule
import com.br.workdate.view.viewmodel.ResumeScheduleViewModel
import kotlinx.android.synthetic.main.fragment_list_release.view.*
import kotlinx.android.synthetic.main.fragment_list_release.view.resume_open
import kotlinx.android.synthetic.main.fragment_list_release.view.resume_paid
import kotlinx.android.synthetic.main.fragment_list_release.view.resume_total
import kotlinx.android.synthetic.main.fragment_list_schedule.*
import kotlinx.android.synthetic.main.fragment_list_schedule.view.*

class ResumeScheduleView(
    private val view: View,
    schedules: MutableList<Schedule>
) {
    private val resume = ResumeScheduleViewModel(schedules)

    companion object DonutAnimation {
        private const val PRIMARY_COLOR = "#311b92"
        const val durationDonutAnimation = 1000L
        val donutSet = listOf(70f)
        val myDonutColors = intArrayOf(
            Color.parseColor(PRIMARY_COLOR),
            Color.WHITE
        )
    }

    fun update() {
        showResumeOpen()
        showResumePaid()
        showTotal()
    }

    private fun showResumeOpen() {
        val total = resume.totalOpen
        view.resume_open.text = total.toString()
    }

    private fun showResumePaid() {
        val total = resume.totalConcluded
        view.resume_paid.text = total.toString()
    }

    private fun showTotal() {
        val total = resume.total
        view.resume_total.text = total.toString()
    }
}