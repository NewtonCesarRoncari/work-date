package com.br.workdate.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import com.br.workdate.extension.limit
import java.io.Serializable

@Entity(indices = [Index(value = ["name"], unique = true)])
class Client(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val phone: String,
    val address: String
) : Serializable {
    @Ignore
    var firstChar: String = ""

    @Ignore
    constructor(
        id: String,
        name: String,
        phone: String,
        address: String,
        firstChar: String
    ) : this(id, name, phone, address) {
        this.firstChar = firstChar
    }

    fun makeClientForLayout(): Client {
        val limitForChar = 26
        return Client(
            this.id,
            this.name.limit(limitForChar),
            this.phone,
            this.address.limit(limitForChar),
            if (this.name.isNotEmpty()) this.name[0].toString() else ""
        )
    }
}