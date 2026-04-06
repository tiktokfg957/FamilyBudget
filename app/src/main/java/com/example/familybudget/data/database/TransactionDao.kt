package com.example.familybudget.data.database

import androidx.room.*
import com.example.familybudget.data.model.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactions ORDER BY date DESC")
    fun getAllTransactions(): Flow<List<Transaction>>

    @Insert
    suspend fun insert(transaction: Transaction)

    @Update
    suspend fun update(transaction: Transaction)

    @Delete
    suspend fun delete(transaction: Transaction)

    @Query("SELECT SUM(amount) FROM transactions WHERE type = 'income'")
    suspend fun getTotalIncome(): Double?

    @Query("SELECT SUM(amount) FROM transactions WHERE type = 'expense'")
    suspend fun getTotalExpense(): Double?
}
