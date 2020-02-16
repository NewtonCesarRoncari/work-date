package com.br.workdate.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.br.workdate.model.Client

@Dao
interface ClientDAO {

    @Insert
    fun insert(client: Client)

    @Update
    fun update(client: Client)

    @Delete
    fun remove(client: Client)

    @Query("SELECT * FROM Client ORDER BY client.name")
    fun listAll(): LiveData<MutableList<Client>>
}