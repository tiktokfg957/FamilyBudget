package com.example.familybudget.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val amount: Double,
    val category: String,
    val dateTimestamp: Long, // храним дату как миллисекунды
    val note: String? = null,
    val type: String // "income" or "expense"
)
