package com.example.familybudget.data.database

import androidx.room.*
import com.example.familybudget.data.model.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactions ORDER BY date DESC")
    fun getAll(): Flow<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE date BETWEEN :start AND :end ORDER BY date DESC")
    fun getBetweenDates(start: Long, end: Long): Flow<List<Transaction>>

    @Insert
    suspend fun insert(transaction: Transaction)

    @Update
    suspend fun update(transaction: Transaction)

    @Delete
    suspend fun delete(transaction: Transaction)

    @Query("SELECT SUM(amount) FROM transactions WHERE type = 'income' AND date BETWEEN :start AND :end")
    suspend fun getTotalIncome(start: Long, end: Long): Long?

    @Query("SELECT SUM(amount) FROM transactions WHERE type = 'expense' AND date BETWEEN :start AND :end")
    suspend fun getTotalExpense(start: Long, end: Long): Long?
}
