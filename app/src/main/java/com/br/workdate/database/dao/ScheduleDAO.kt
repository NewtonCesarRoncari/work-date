package com.br.workdate.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import androidx.sqlite.db.SupportSQLiteQuery
import com.br.workdate.model.Schedule

@Dao
interface ScheduleDAO {

    @Insert(onConflict = REPLACE)
    fun insert(schedule: Schedule)

    @Update
    fun update(schedule: Schedule)

    @Delete
    fun remove(schedule: Schedule)

    @Query("SELECT * FROM Schedule ORDER BY schedule.hour")
    fun listAll(): LiveData<MutableList<Schedule>>

    @RawQuery(observedEntities = [Schedule::class])
    fun findScheduleFilter(query: SupportSQLiteQuery): LiveData<MutableList<Schedule>>
}