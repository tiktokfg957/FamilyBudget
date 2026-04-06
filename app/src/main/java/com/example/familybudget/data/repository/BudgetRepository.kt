package com.example.familybudget.data.repository

import com.example.familybudget.data.database.AppDatabase
import com.example.familybudget.data.model.Category
import com.example.familybudget.data.model.Transaction
import kotlinx.coroutines.flow.Flow

class BudgetRepository(private val db: AppDatabase) {

    fun getAllTransactions(): Flow<List<Transaction>> = db.transactionDao().getAll()
    fun getTransactionsBetween(start: Long, end: Long): Flow<List<Transaction>> = db.transactionDao().getBetweenDates(start, end)
    suspend fun insertTransaction(transaction: Transaction) = db.transactionDao().insert(transaction)
    suspend fun deleteTransaction(transaction: Transaction) = db.transactionDao().delete(transaction)

    fun getAllCategories(): Flow<List<Category>> = db.categoryDao().getAll()
    fun getCategoriesByType(isIncome: Boolean): Flow<List<Category>> = db.categoryDao().getByType(isIncome)
    suspend fun insertCategory(category: Category) = db.categoryDao().insert(category)
    suspend fun deleteCategory(category: Category) = db.categoryDao().delete(category)

    suspend fun getTotalIncome(start: Long, end: Long): Long = db.transactionDao().getTotalIncome(start, end) ?: 0L
    suspend fun getTotalExpense(start: Long, end: Long): Long = db.transactionDao().getTotalExpense(start, end) ?: 0L
}
