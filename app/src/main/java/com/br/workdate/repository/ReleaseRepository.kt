package com.br.workdate.repository

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.MutableLiveData
import androidx.sqlite.db.SimpleSQLiteQuery
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
    var releasesReturned = MutableLiveData<MutableList<Release>>().apply { postValue(null) }

    fun insert(release: Release) =
        scope.launch {
            tryRequisition(
                requisition = { dao.insert(release) },
                inFailureCase = {},
                inSuccessCase = {})
        }

    fun update(release: Release) =
        scope.launch {
            tryRequisition(
                requisition = { dao.update(release) },
                inFailureCase = {},
                inSuccessCase = {})
        }

    fun listAll(situation: Situation) = dao.listAll(situation)

    fun listAll() = dao.listAll()

    fun findReleaseIdByScheduleId(scheduleId: String) = dao.findReleaseIdByScheduleId(scheduleId)

    fun findReleaseFilter(query: String) {
        var releases = mutableListOf<Release>()
        val simpleSQLiteQuery = SimpleSQLiteQuery(query)
        scope.launch {
            releases = dao.findReleaseFilter(simpleSQLiteQuery)
        }
        Thread.sleep(1000)
        releasesReturned.value = releases
    }

    private fun tryRequisition(
        requisition: () -> Unit,
        inFailureCase: () -> Unit,
        inSuccessCase: () -> Unit
    ) {
        var failure = false
        try {
            requisition()
        } catch (e: SQLiteConstraintException) {
            e.printStackTrace()
            failure = true
            inFailureCase()
        } finally {
            if (!failure) {
                inSuccessCase()
            }
        }
    }
}