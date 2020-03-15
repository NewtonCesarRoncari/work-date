package com.br.workdate.view.viewmodel

import com.br.workdate.model.Release
import com.br.workdate.model.Resume

class ResumeViewModel(releases: MutableList<Release>) {

    private val resume = Resume(releases)

    val totalOpen get() = resume.totalOpen

    val totalPaid get() = resume.totalPaid

    val total get() = resume.total
}