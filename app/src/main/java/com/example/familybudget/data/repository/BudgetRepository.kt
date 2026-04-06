package com.example.familybudget.data.repository

import com.example.familybudget.data.database.AppDatabase
import com.example.familybudget.data.model.Transaction
import kotlinx.coroutines.flow.Flow

class BudgetRepository(private val db: AppDatabase) {

    fun getAllTransactions(): Flow<List<Transaction>> = db.transactionDao().getAllTransactions()
    suspend fun insertTransaction(transaction: Transaction) = db.transactionDao().insert(transaction)
    suspend fun updateTransaction(transaction: Transaction) = db.transactionDao().update(transaction)
    suspend fun deleteTransaction(transaction: Transaction) = db.transactionDao().delete(transaction)

    suspend fun getTotalIncome(): Double = db.transactionDao().getTotalIncome() ?: 0.0
    suspend fun getTotalExpense(): Double = db.transactionDao().getTotalExpense() ?: 0.0

    suspend fun initDatabase() {
        // nothing to prefill
    }
}
