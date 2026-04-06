package com.example.familybudget.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val iconRes: Int = 0,
    val isIncome: Boolean = false, // если true – категория дохода (например, зарплата), false – расход
    val parentId: Long? = null     // для иерархии
)
