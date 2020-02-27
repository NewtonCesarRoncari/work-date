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

    @Query("SELECT name FROM Client WHERE client.id = :id")
    fun returnNameForId(id: String): LiveData<String>

    @Query("SELECT * FROM Client WHERE client.id = :id")
    fun returnForId(id: String): LiveData<Client>
}