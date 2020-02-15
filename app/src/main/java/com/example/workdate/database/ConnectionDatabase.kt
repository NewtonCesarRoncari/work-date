package com.example.workdate.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.workdate.database.dao.ClientDAO
import com.example.workdate.model.Client

@Database(version = 1, entities = [Client::class], exportSchema = false)
abstract class ConnectionDatabase : RoomDatabase() {
    abstract fun clientDao(): ClientDAO
}