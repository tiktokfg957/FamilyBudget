package com.example.familybudget

import android.app.Application
import com.example.familybudget.data.database.AppDatabase
import com.example.familybudget.data.repository.BudgetRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FamilyBudgetApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { BudgetRepository(database) }

    override fun onCreate() {
        super.onCreate()
        // инициализация БД (создание таблиц) не требует данных, поэтому просто запускаем
        CoroutineScope(Dispatchers.IO).launch {
            repository.initDatabase() // может быть пустым
        }
    }
}
