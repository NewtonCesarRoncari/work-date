package com.br.workdate.repository

import com.br.workdate.model.Release
import com.br.workdate.model.ResumeRelease

class ResumeReleaseRepository(releases: MutableList<Release>) {

    private val resume = ResumeRelease(releases)

    val totalOpen get() = resume.totalOpen

    val totalPaid get() = resume.totalPaid

    val total get() = resume.total
}