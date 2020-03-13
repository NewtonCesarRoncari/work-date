package com.br.workdate.repository

import androidx.lifecycle.LiveData
import com.br.workdate.database.dao.ReleaseDAO
import com.br.workdate.model.Release
import com.br.workdate.model.Situation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ReleaseRepository(private val dao: ReleaseDAO) {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    fun insert(release: Release) {
        scope.launch {
            dao.insert(release)
        }
    }

    fun update(release: Release) {
        scope.launch {
            dao.update(release)
        }
    }

    fun listAll(situation: Situation): LiveData<MutableList<Release>> = dao.listAll(situation)
}