package com.br.workdate.model

import android.content.Context
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import com.br.workdate.extension.formatCoin
import com.br.workdate.extension.limit
import java.io.Serializable
import java.math.BigDecimal

@Entity(indices = [Index(value = ["description"], unique = true)])
class Service(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val description: String,
    val value: BigDecimal
) : Serializable {
    @Ignore
    var firstChar: String = ""

    @Ignore
    var formatValue: String = ""

    @Ignore
    constructor(
        id: String,
        description: String,
        value: BigDecimal,
        firstChar: String,
        formatValue: String
    ) : this(id, description, value) {
        this.firstChar = firstChar
        this.formatValue = formatValue
    }

    fun makeServiceForLayout(context: Context): Service {
        val limitForChar = 26
        return Service(
            this.id,
            this.description.limit(limitForChar),
            this.value,
            if (this.description.isNotEmpty()) this.description[0].toString() else "",
            this.value.formatCoin(context)
        )
    }
}