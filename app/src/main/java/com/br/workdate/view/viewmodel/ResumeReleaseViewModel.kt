package com.br.workdate.view.viewmodel

import com.br.workdate.model.Release
import com.br.workdate.repository.ResumeReleaseRepository

class ResumeReleaseViewModel(releases: MutableList<Release>) {

    private val repository = ResumeReleaseRepository(releases)

    val totalOpen get() = repository.totalOpen

    val totalPaid get() = repository.totalPaid

    val total get() = repository.total
}