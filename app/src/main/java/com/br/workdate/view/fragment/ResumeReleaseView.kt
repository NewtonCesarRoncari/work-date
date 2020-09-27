package com.br.workdate.view.fragment

import android.view.View
import com.br.workdate.extension.formatCoin
import com.br.workdate.model.Release
import com.br.workdate.view.viewmodel.ResumeReleaseViewModel
import kotlinx.android.synthetic.main.fragment_list_release.view.*

class ResumeReleaseView(
    private val view: View,
    releases: MutableList<Release>
) {

    private val resume = ResumeReleaseViewModel(releases)

    fun update() {
        showResumeOpen()
        showResumePaid()
        showTotal()
    }

    private fun showResumeOpen() {
        val total = resume.totalOpen
        view.resume_open.text = total.formatCoin(view.context)
    }

    private fun showResumePaid() {
        val total = resume.totalPaid
        view.resume_paid.text = total.formatCoin(view.context)
    }

    private fun showTotal() {
        val total = resume.total
        view.resume_total.text = total.formatCoin(view.context)
    }
}