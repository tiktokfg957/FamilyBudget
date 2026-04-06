package com.example.familybudget.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.familybudget.data.model.Transaction
import com.example.familybudget.data.repository.BudgetRepository
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel(private val repository: BudgetRepository) : ViewModel() {

    val transactions = repository.getAllTransactions().asLiveData()

    private val _balance = MutableLiveData(0L)
    val balance: LiveData<Long> = _balance

    init {
        loadBalance()
    }

    private fun loadBalance() {
        viewModelScope.launch {
            val now = System.currentTimeMillis()
            val startOfMonth = getStartOfMonth()
            val endOfMonth = getEndOfMonth()
            val income = repository.getTotalIncome(startOfMonth, endOfMonth)
            val expense = repository.getTotalExpense(startOfMonth, endOfMonth)
            _balance.postValue(income - expense)
        }
    }

    private fun getStartOfMonth(): Long {
        val cal = Calendar.getInstance()
        cal.set(Calendar.DAY_OF_MONTH, 1)
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        return cal.timeInMillis
    }

    private fun getEndOfMonth(): Long {
        val cal = Calendar.getInstance()
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH))
        cal.set(Calendar.HOUR_OF_DAY, 23)
        cal.set(Calendar.MINUTE, 59)
        cal.set(Calendar.SECOND, 59)
        return cal.timeInMillis
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
