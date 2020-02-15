package com.example.workdate.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.workdate.database.converter.BigDecimalConverter
import com.example.workdate.database.dao.ClientDAO
import com.example.workdate.database.dao.ServiceDAO
import com.example.workdate.model.Client
import com.example.workdate.model.Service

@Database(version = 1, entities = [Client::class, Service::class], exportSchema = false)
@TypeConverters(BigDecimalConverter::class)
abstract class ConnectionDatabase : RoomDatabase() {
    abstract fun clientDao(): ClientDAO
    abstract fun serviceDao(): ServiceDAO
}