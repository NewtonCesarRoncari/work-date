package com.br.workdate.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.br.workdate.model.Release
import com.br.workdate.model.Type

@Dao
interface ReleaseDAO {

    @Insert
    fun insert(release: Release)

    @Update
    fun update(release: Release)

    @Query("SELECT * FROM `Release` WHERE type = :type")
    fun listAll(type: Type): LiveData<MutableList<Release>>
}