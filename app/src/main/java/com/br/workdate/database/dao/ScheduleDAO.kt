package com.br.workdate.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.br.workdate.model.Schedule

@Dao
interface ScheduleDAO {

    @Insert
    fun insert(schedule: Schedule)

    @Update
    fun update(schedule: Schedule)

    @Delete
    fun remove(schedule: Schedule)

    @Query("SELECT * FROM Schedule ORDER BY schedule.hour")
    fun listAll(): LiveData<MutableList<Schedule>>
}