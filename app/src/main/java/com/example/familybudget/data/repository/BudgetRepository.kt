package com.example.familybudget.data.repository

import com.example.familybudget.data.database.AppDatabase
import com.example.familybudget.data.model.Category
import com.example.familybudget.data.model.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class BudgetRepository(private val db: AppDatabase) {

    fun getAllTransactions(): Flow<List<Transaction>> = db.transactionDao().getAllTransactions()
    suspend fun insertTransaction(transaction: Transaction) = db.transactionDao().insert(transaction)
    suspend fun updateTransaction(transaction: Transaction) = db.transactionDao().update(transaction)
    suspend fun deleteTransaction(transaction: Transaction) = db.transactionDao().delete(transaction)

    fun getAllCategories(): Flow<List<Category>> = db.categoryDao().getAllCategories()
    suspend fun insertCategory(category: Category) = db.categoryDao().insert(category)

    suspend fun getTotalIncome(): Double = db.transactionDao().getTotalIncome() ?: 0.0
    suspend fun getTotalExpense(): Double = db.transactionDao().getTotalExpense() ?: 0.0

    suspend fun initDatabase() {
        val categories = db.categoryDao().getAllCategories().first()
        if (categories.isEmpty()) {
            val defaultCategories = listOf(
                Category(name = "Еда"),
                Category(name = "Транспорт"),
                Category(name = "Жильё"),
                Category(name = "Развлечения"),
                Category(name = "Здоровье"),
                Category(name = "Одежда"),
                Category(name = "Образование"),
                Category(name = "Другое")
            )
            defaultCategories.forEach { db.categoryDao().insert(it) }
        }
    }
}
