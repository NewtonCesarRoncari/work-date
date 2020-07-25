package com.br.workdate.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.br.workdate.model.Release
import com.br.workdate.model.Situation

@Dao
interface ReleaseDAO {

    @Insert
    fun insert(release: Release)

    @Update
    fun update(release: Release)

    @Query("SELECT * FROM `Release` WHERE situation = :situation")
    fun listAll(situation: Situation): LiveData<MutableList<Release>>

    @Query("SELECT * FROM `Release`")
    fun listAll(): LiveData<MutableList<Release>>

    @Query("SELECT id FROM `Release` WHERE schedule_id = :scheduleId LIMIT 1")
    fun findReleaseIdByScheduleId(scheduleId: String): LiveData<String>

    @RawQuery(observedEntities = [Release::class])
    fun findReleaseFilter(query: SupportSQLiteQuery): MutableList<Release>
}