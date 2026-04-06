package com.example.familybudget

import android.app.Application
import com.example.familybudget.data.database.AppDatabase
import com.example.familybudget.data.repository.BudgetRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { BudgetRepository(database) }

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.IO).launch {
            repository.initCategoriesIfEmpty()
        }
    }
}
