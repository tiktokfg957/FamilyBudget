package com.example.familybudget.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.familybudget.data.model.Transaction
import com.example.familybudget.data.repository.BudgetRepository
import kotlinx.coroutines.launch
import java.util.Date

class AddTransactionViewModel(private val repository: BudgetRepository) : ViewModel() {

    fun addTransaction(amount: Double, category: String, date: Date, note: String, type: String) {
        viewModelScope.launch {
            val transaction = Transaction(
                amount = amount,
                category = category,
                dateTimestamp = date.time,
                note = note,
                type = type
            )
            repository.insertTransaction(transaction)
        }
    }

    class AddTransactionViewModelFactory(private val repository: BudgetRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AddTransactionViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AddTransactionViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
