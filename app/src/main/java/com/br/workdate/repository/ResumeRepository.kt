package com.br.workdate.repository

import com.br.workdate.model.Release
import com.br.workdate.model.Resume

class ResumeRepository(releases: MutableList<Release>) {

    private val resume = Resume(releases)

    val totalOpen get() = resume.totalOpen

    val totalPaid get() = resume.totalPaid

    val total get() = resume.total
}