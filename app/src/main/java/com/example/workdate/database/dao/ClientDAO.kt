package com.example.workdate.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.workdate.model.Client

@Dao
interface ClientDAO {

    @Insert
    fun insert(client: Client)

    @Update
    fun update(client: Client)

    @Delete
    fun remove(client: Client)

    @Query("SELECT * FROM Client")
    fun listAll(): LiveData<MutableList<Client>>
}