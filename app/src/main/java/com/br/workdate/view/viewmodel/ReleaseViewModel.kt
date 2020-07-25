package com.br.workdate.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.br.workdate.model.Release
import com.br.workdate.model.Situation
import com.br.workdate.repository.ReleaseRepository

class ReleaseViewModel(private val repository: ReleaseRepository) : ViewModel() {

    fun insert(release: Release) = repository.insert(release)

    fun update(release: Release) = repository.update(release)

    fun listAll(situation: Situation) = repository.listAll(situation)

    fun listAll() = repository.listAll()

    fun findReleaseIdByScheduleId(scheduleId: String) =
        repository.findReleaseIdByScheduleId(scheduleId)

    fun findReleaseFilter(query: String) = repository.findReleaseFilter(query)

    fun checkReleasesReturned(): LiveData<MutableList<Release>>? = repository.releasesReturned
}