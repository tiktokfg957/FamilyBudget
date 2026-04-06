package com.example.familybudget.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val amount: Long,               // сумма в копейках (или целое число)
    val categoryId: Long,
    val date: Long,                 // timestamp
    val note: String? = null,
    val type: String,               // "income" или "expense"
    val isRecurring: Boolean = false,
    val recurringPeriod: String? = null, // "weekly", "monthly", "yearly"
    val photoUri: String? = null
)
