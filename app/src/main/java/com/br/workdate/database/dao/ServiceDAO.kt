package com.br.workdate.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.br.workdate.model.Service

@Dao
interface ServiceDAO {

    @Insert
    fun insert(service: Service)

    @Update
    fun update(service: Service)

    @Delete
    fun remove(service: Service)

    @Query("SELECT * FROM Service ORDER BY service.description")
    fun listAll(): LiveData<MutableList<Service>>

    @Query("SELECT description FROM Service WHERE service.id = :id LIMIT 1")
    fun returnDescriptionForId(id: String): LiveData<String>

    @Query("SELECT description FROM Service")
    fun returnAllServicesDescriptions(): LiveData<List<String>>

    @Query("SELECT * FROM Service WHERE service.id = :id LIMIT 1")
    fun returnForId(id: String): LiveData<Service>
}