package com.br.workdate.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.br.workdate.model.Release
import com.br.workdate.repository.ReleaseRepository

class ReleaseViewModel(private val repository: ReleaseRepository) : ViewModel() {

    fun insert(release: Release) = repository.insert(release)

    fun update(release: Release) = repository.update(release)

    fun listAll(): LiveData<List<Release>> = repository.listAll()
}