package com.br.workdate.view.fragment

import android.graphics.Color
import android.view.View
import com.br.workdate.extension.percentage
import com.br.workdate.model.Schedule
import com.br.workdate.view.viewmodel.ResumeScheduleViewModel
import kotlinx.android.synthetic.main.fragment_list_release.view.resume_open
import kotlinx.android.synthetic.main.fragment_list_release.view.resume_paid
import kotlinx.android.synthetic.main.fragment_list_release.view.resume_total
import kotlinx.android.synthetic.main.fragment_list_schedule.view.*

class ResumeScheduleView(
    private val view: View,
    private val schedules: MutableList<Schedule>
) {
    private val resume = ResumeScheduleViewModel(schedules)

    companion object DonutAnimation {
        private var percentage: Float = 0f
        private const val PRIMARY_COLOR = "#311b92"
        const val durationDonutAnimation = 1000L
        var donutSet = listOf(percentage)
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
        val totalConcluded = resume.totalConcluded
        view.resume_paid.text = totalConcluded.toString()
        updatePercentageGraphic(totalConcluded)
    }

    private fun showTotal() {
        val total = resume.total
        view.resume_total.text = total.toString()
    }

    private fun updatePercentageGraphic(total: Int) {
        percentage = resume.returnPercentage(schedules.size, total).toFloat()
        view.percentage.text = percentage.toInt().toString().percentage()
        donutSet = listOf(percentage)
    }
}