package com.example.familybudget.ui.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.familybudget.data.repository.BudgetRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class StatisticsViewModel(private val repository: BudgetRepository) : ViewModel() {

    private val _categoryExpenses = MutableLiveData<Map<String, Double>>()
    val categoryExpenses: LiveData<Map<String, Double>> = _categoryExpenses

    init {
        loadCategoryExpenses()
    }

    private fun loadCategoryExpenses() {
        viewModelScope.launch {
            val transactions = repository.getAllTransactions().first()
            val expenses = transactions.filter { it.type == "expense" }
            val categoryMap = expenses.groupBy { it.category }.mapValues { it.value.sumOf { trans -> trans.amount } }
            _categoryExpenses.postValue(categoryMap)
        }
    }

    class StatisticsViewModelFactory(private val repository: BudgetRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(StatisticsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return StatisticsViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
