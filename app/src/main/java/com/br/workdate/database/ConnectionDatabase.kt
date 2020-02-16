package com.br.workdate.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.br.workdate.database.converter.BigDecimalConverter
import com.br.workdate.database.dao.ClientDAO
import com.br.workdate.database.dao.ServiceDAO
import com.br.workdate.model.Client
import com.br.workdate.model.Service

@Database(version = 1, entities = [Client::class, Service::class], exportSchema = false)
@TypeConverters(BigDecimalConverter::class)
abstract class ConnectionDatabase : RoomDatabase() {
    abstract fun clientDao(): ClientDAO
    abstract fun serviceDao(): ServiceDAO
}