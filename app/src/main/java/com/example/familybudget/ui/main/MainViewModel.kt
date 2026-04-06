package com.example.familybudget.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.familybudget.data.model.Transaction
import com.example.familybudget.data.repository.BudgetRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: BudgetRepository) : ViewModel() {

    val transactions = repository.getAllTransactions().asLiveData()
    val totalIncome = repository.getTotalIncome().asLiveData()
    val totalExpense = repository.getTotalExpense().asLiveData()

    suspend fun deleteTransaction(transaction: Transaction) {
        repository.deleteTransaction(transaction)
    }

    class MainViewModelFactory(private val repository: BudgetRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
